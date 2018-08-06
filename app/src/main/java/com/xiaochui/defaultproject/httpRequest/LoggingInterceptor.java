package com.xiaochui.defaultproject.httpRequest;



import com.xiaochui.defaultproject.AppBuildConfig;
import com.xiaochui.defaultproject.XiaoChuiApplication;
import com.xiaochui.defaultproject.utils.SP;
import com.xiaochui.defaultproject.utils.ShowLog;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @author Death丶Love
 * @project_name XiaoChuiProject
 * @package_name com.xiaochui.mainlibrary.optionConfiguration.netConfiguration
 * @time 2018/5/3 9:15
 * @remark
 */
public class LoggingInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        //这个chain里面包含了request和response，所以你要什么都可以从这里拿
        Request request = chain.request().newBuilder()
                .addHeader("platform", AppBuildConfig.PLATFORM)
                .addHeader("model", String.valueOf(1))
                .addHeader("appver", AppBuildConfig.VERSION_NAME)
                .addHeader("token", SP.getToken(XiaoChuiApplication.getInstance()))
                .build();

        long startNs = System.nanoTime();//请求发起的时间
        ShowLog.i("HttpLogger", String.format("    %n<----START%nsendRequest:%s%n" +
                "startTime:%s%n" +
                "headers:{%n%s}%n" +
                "END---->%n" +
                "    ", request.url(), formatDate(System.currentTimeMillis()), request.headers()));

        Response response = chain.proceed(request);

        long endTime = System.nanoTime();//收到响应的时间

        //这里不能直接使用response.body().string()的方式输出日志
        //因为response.body().string()之后，response中的流会被关闭，程序会报错，我们需要创建出一
        //个新的response给应用层处理
        ResponseBody responseBody = response.peekBody(1024 * 1024);

        ShowLog.d("HttpLogger", String.format("   %n<----START%n" +
                        "method:%s          requestUrl:%s%n" +
                        "responseTime:%s%n" +
                        "elapsed-time:%sms%n" +
                        "content-length:%s%n" +
                        "json:%s%n" +
                        "END---->%n" +
                        "     ",
                split(response.request().url().toString()),
                response.request().url(),
                formatDate(System.currentTimeMillis()),
                TimeUnit.NANOSECONDS.toMillis(endTime - startNs),
                responseBody.contentLength(),
                responseBody.string()));

        return response;
    }

    private String split(String url) {
        if (url.contains("?")) {
            return url.substring(url.lastIndexOf("/") + 1, url.indexOf("?"));
        } else {
            return url.substring(url.lastIndexOf("/") + 1, url.length());
        }

    }

    public static String formatDate(long mileSeconds) {
        SimpleDateFormat dateFormat = null;
        dateFormat = new SimpleDateFormat("yyyy/MM/dd  HH:mm:ss");
        return dateFormat.format(mileSeconds);
    }

    /**
     * 格式化json字符串
     *
     * @param jsonStr 需要格式化的json串
     * @return 格式化后的json串
     */
    public static String formatJson(String jsonStr) {
        if (null == jsonStr || "".equals(jsonStr)) return "";
        StringBuilder sb = new StringBuilder();
        char last = '\0';
        char current = '\0';
        int indent = 0;
        for (int i = 0; i < jsonStr.length(); i++) {
            last = current;
            current = jsonStr.charAt(i);
            //遇到{ [换行，且下一行缩进
            switch (current) {
                case '{':
                case '[':
                    sb.append(current);
                    sb.append('\n');
                    indent++;
                    addIndentBlank(sb, indent);
                    break;
                //遇到} ]换行，当前行缩进
                case '}':
                case ']':
                    sb.append('\n');
                    indent--;
                    addIndentBlank(sb, indent);
                    sb.append(current);
                    break;
                //遇到,换行
                case ',':
                    sb.append(current);
                    if (last != '\\') {
                        sb.append('\n');
                        addIndentBlank(sb, indent);
                    }
                    break;
                default:
                    sb.append(current);
            }
        }
        return sb.toString();
    }

    /**
     * 添加space
     *
     * @param sb
     * @param indent
     */
    private static void addIndentBlank(StringBuilder sb, int indent) {
        for (int i = 0; i < indent; i++) {
            sb.append('\t');
        }
    }
}