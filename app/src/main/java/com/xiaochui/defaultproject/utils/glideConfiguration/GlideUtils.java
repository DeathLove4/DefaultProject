package com.xiaochui.defaultproject.utils.glideConfiguration;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xiaochui.defaultproject.R;

import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * @author Death丶Love
 * @project_name XiaoChuiProject
 * @package_name com.xiaochui.mainlibrary.optionConfiguration.glideConfiguration
 * @time 2017/10/9 10:39
 * @remark
 */

public class GlideUtils {

    /**
     * 普通显示
     *
     * @param context
     * @param res
     * @param imageView
     */
    public static void show(Context context, Object res, ImageView imageView) {
        if (res instanceof String) {
            Glide.with(context).load((String) res).placeholder(R.mipmap.img_failed).error(R.mipmap.img_failed).crossFade().into(imageView);
        } else if (res instanceof Integer) {
            Glide.with(context).load((Integer) res).placeholder(R.mipmap.img_failed).error(R.mipmap.img_failed).crossFade().into(imageView);
        }
    }

    /**
     * 显示圆形图片
     *
     * @param context
     * @param res
     * @param imageView
     */
    public static void showCircle(Context context, Object res, ImageView imageView) {
        if (res instanceof String) {
            Glide.with(context).load((String) res).placeholder(R.mipmap.img_failed).error(R.mipmap.img_failed).transform(new GlideCircle(context)).crossFade().into(imageView);
        } else if (res instanceof Integer) {
            Glide.with(context).load((Integer) res).placeholder(R.mipmap.img_failed).error(R.mipmap.img_failed).transform(new GlideCircle(context)).crossFade().into(imageView);
        }
    }

    /**
     * 显示高斯模糊
     *
     * @param context
     * @param res
     * @param imageView
     * @param radius
     * @param sampling
     */
    public static void showGaussianBlur(Context context, Object res, ImageView imageView, int radius, int sampling) {
        if (res instanceof String) {
            Glide.with(context).load((String) res).placeholder(R.mipmap.img_failed).error(R.mipmap.img_failed).bitmapTransform(new BlurTransformation(context, radius, sampling)).crossFade().into(imageView);
        } else if (res instanceof Integer) {
            Glide.with(context).load((Integer) res).placeholder(R.mipmap.img_failed).error(R.mipmap.img_failed).bitmapTransform(new BlurTransformation(context, radius, sampling)).crossFade().into(imageView);
        }
    }
}
