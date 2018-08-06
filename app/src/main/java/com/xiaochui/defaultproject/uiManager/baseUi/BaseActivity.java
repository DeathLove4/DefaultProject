package com.xiaochui.defaultproject.uiManager.baseUi;

import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.xiaochui.defaultproject.R;
import com.xiaochui.defaultproject.modelSet.BaseEventModel;
import com.xiaochui.defaultproject.modelSet.FunctionParamesModel;
import com.xiaochui.defaultproject.utils.DialogUtils;
import com.xiaochui.defaultproject.view.AddStatusBarPadding;
import com.xiaochui.defaultproject.view.swipebacklayout.SwipeBackActivityBase;
import com.xiaochui.defaultproject.view.swipebacklayout.SwipeBackActivityHelper;
import com.xiaochui.defaultproject.view.swipebacklayout.SwipeBackLayout;
import com.xiaochui.defaultproject.view.swipebacklayout.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Death丶Love
 * @project_name XiaoChuiProject
 * @package_name com.xiaochui.mainlibrary.baseUiManage
 * @time 2017/7/25 10:29
 * @remark activity基类
 */

public class BaseActivity extends AppCompatActivity implements SwipeBackActivityBase {
    private BaseActivity mActivity;
    private SwipeBackActivityHelper swipeBackActivityHelper;
    public String NODATA = "暂无数据";
    public String NOMOREDATA = "没有更多数据了";
    public String DATAERROR = "数据错误，请稍后重试";
    /**
     * loading
     */
    private Dialog loadingDialog;

    public Unbinder unbinder;
    /**
     * 状态栏是否需要手动着色
     */
    protected boolean needAddBar = true;
    private View statusBar = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        swipeBackActivityHelper = new SwipeBackActivityHelper(this);
        swipeBackActivityHelper.onActivityCreate();
        registerEventBus();
    }

    @Override
    public void setContentView(int layoutResID) {
        if (needAddBar && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            statusBar = AddStatusBarPadding.getStatusBar(this);
            super.setContentView(AddStatusBarPadding.getViewGroup(this, LayoutInflater.from(this).inflate(layoutResID, null), statusBar));
        } else {
            super.setContentView(layoutResID);
        }
    }

    public void initEvent() {
    }

    public void initHeaderLayout() {
    }


    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return swipeBackActivityHelper.getSwipeBackLayout();
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }

    @Override
    public void scrollToFinishActivity() {
        Utils.convertActivityToTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        swipeBackActivityHelper.onPostCreate();
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_remain);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode, Bundle options) {
        super.startActivityForResult(intent, requestCode, options);
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_remain);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, R.anim.slide_right_out);

//        if (MainActivity.instance != null) {
//            ShowLog.i("MainActivity", "---- main - on");
//        } else {
//            ShowLog.i("MainActivity", "---- main - off");
//        }
    }

    public void bindbutterknife() {
        unbinder = ButterKnife.bind(this);
    }

    public void registerEventBus() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    /**
     * 接收eventBus传输的数据
     *
     * @param baseEventModel
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(BaseEventModel baseEventModel) {
    }

    public void parseBaseEventModel(BaseActivity baseActivity, BaseEventModel baseEventModel) {
        switch (baseEventModel.getType()) {
            case CUSTOMJPUSHMESSAGE:
                if (isTop(baseActivity, new Intent(baseActivity, baseActivity.getClass()))) {
//                    CustomJpushMessageModel model = (CustomJpushMessageModel) baseEventModel.getObj();
//                    showDialog(baseActivity, model.getTitle(), model.getLeftBtnContent(), model.getRightBtnContent(), model.getAndroidTurnParameter());
                }
                break;
        }
    }

    public void showDialog(final BaseActivity baseActivity, String message, String leftBtnContent, String rightBtnContent, final String option) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(baseActivity);
        builder.setTitle("温馨提示");
        builder.setMessage(message);
        builder.setIcon(R.mipmap.app_icon);
        builder.setPositiveButton(rightBtnContent, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    Gson gson = new Gson();
                    FunctionParamesModel functionParamesModel = gson.fromJson(option, FunctionParamesModel.class);
                    Intent i = new Intent();
                    //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.setClass(baseActivity, Class.forName(functionParamesModel.getClassName()));
                    for (FunctionParamesModel.ParamsListBean bean : functionParamesModel.getParamsList()) {
                        i.putExtra(bean.getK(), bean.getV());
                    }
                    i.putExtra(FunctionParamesModel.BGTRANSPARENCY, functionParamesModel.getBgTransparency());
                    baseActivity.startActivity(i);
                } catch (Exception e) {
                }
            }
        });
        builder.setNegativeButton(leftBtnContent, null);

        // 创建并显示
        builder.create().show();
    }

    public void toast(int resId, boolean isShort) {
        toast(getString(resId), isShort);
    }

    public void toast(String text, boolean isShort) {
        if (isShort) {
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, text, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * @param cancelable 点击返回按钮是否可以关闭loading
     */
    public void showLoading(boolean cancelable) {
        if (loadingDialog == null) {
            loadingDialog = DialogUtils.createLoadingDialog(this, "请稍等...");
        }
        loadingDialog.show();
        loadingDialog.setCancelable(cancelable);
    }

    /**
     * @param cancelable 点击返回按钮是否可以关闭loading
     */
    public void showLoading(boolean cancelable, String showContent) {
        if (loadingDialog == null) {
            loadingDialog = DialogUtils.createLoadingDialog(this, showContent);
        }
        loadingDialog.show();
        loadingDialog.setCancelable(cancelable);
    }

    public void hideLoading() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
    }

    public boolean checkPermission(String... permissionArray) {
        for (String permission : permissionArray) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 向用户动态申请权限
     *
     * @param requestCode
     * @param permissions
     */
    public void requestPermissions(int requestCode, String... permissions) {
        ActivityCompat.requestPermissions(this, permissions, requestCode);
    }

    /**
     * 判断所有的权限是否授予
     *
     * @param grantResults
     * @return
     */
    public boolean checkAllPermissionGranted(int[] grantResults) {
        boolean isAllGranted = true;
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                isAllGranted = false;
                break;
            }
        }
        return isAllGranted;
    }

    public void startAppDetailsSettingActivity() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName())); // 根据包名打开对应的设置界面
        startActivity(intent);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    public String notNull(String obj) {
        return obj != null ? obj : "";
    }

    public boolean legalString(String obj) {
        return obj != null && !obj.equals("");
    }

    public void hideLoadingRefresh(RefreshLayout refreshLayout) {
        hideLoading();
        if (refreshLayout != null) {
            if (refreshLayout.isLoading()) {
                refreshLayout.finishLoadmore();
            }
            if (refreshLayout.isRefreshing()) {
                refreshLayout.finishRefresh();
            }
        }
    }

    public void toast(String toastMsg) {
        toast(toastMsg, true);
    }

    private void isTopActivity() {
//        boolean isTop = false;
//        ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
//        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
//        DebugLog.d(TAG, "isTopActivity = " + cn.getClassName());
//        if (cn.getClassName().contains(TAG)) {
//            isTop = true;
//        }
//        return isTop;
    }

    public static boolean isTop(Context context, Intent intent) {
        ActivityManager am = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> appTask = am.getRunningTasks(1);
        if (appTask.size() > 0 && appTask.get(0).topActivity.equals(intent.getComponent())) {
            return true;
        } else {
            return false;
        }
    }
}
