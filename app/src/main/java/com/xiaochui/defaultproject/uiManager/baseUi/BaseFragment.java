package com.xiaochui.defaultproject.uiManager.baseUi;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.xiaochui.defaultproject.R;
import com.xiaochui.defaultproject.modelSet.BaseEventModel;
import com.xiaochui.defaultproject.utils.DialogUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * @author Death丶Love
 * @project_name XiaoChuiProject
 * @package_name com.xiaochui.mainlibrary.baseUiManage
 * @time 2017/7/27 10:07
 * @remark fragment基类
 */

public abstract class BaseFragment extends Fragment {
    private Dialog loadingDialog;
    private BaseFragment mBaseFragment;
    protected String NODATA = "暂无数据";
    protected String NOMOREDATA = "没有更多数据了";
    protected String SERVER_ERROR = "数据错误，请稍后再试";
    /**
     * 标记当前fragment用户是否可见
     */
    protected boolean isVisible;

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBaseFragment = this;
        return inflater.inflate(getContentViewId(), container, false);
    }

    public abstract int getContentViewId();

    protected abstract void initEvent(Bundle savedInstanceState);

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (unbinder == null) {
            unbinder = ButterKnife.bind(this, view);
        }
        initEvent(savedInstanceState);
    }


    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        getActivity().overridePendingTransition(R.anim.slide_right_in, R.anim.slide_remain);
    }


    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        getActivity().overridePendingTransition(R.anim.slide_right_in, R.anim.slide_remain);
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

    public void bindbutterknife(View rootView) {
        unbinder = ButterKnife.bind(this, rootView);
    }


    public void toast(int resId, boolean isShort) {
        toast(getString(resId), isShort);
    }

    public void toast(String text, boolean isShort) {
        if (isShort) {
            Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show();
        }
    }

    public void showLoading(boolean cancelable) {
        if (loadingDialog == null) {
            loadingDialog = DialogUtils.createLoadingDialog(getActivity(), "请稍等...");
        }
        loadingDialog.show();
        loadingDialog.setCancelable(cancelable);
    }

    public void hideLoading() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }

    public boolean checkPermission(String... permissionArray) {
        for (String permission : permissionArray) {
            if (ContextCompat.checkSelfPermission(getContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public void startAppDetailsSettingActivity() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getActivity().getPackageName())); // 根据包名打开对应的设置界面
        startActivity(intent);
    }


    /**
     * 在这里实现Fragment数据的缓加载.
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    protected void onVisible() {
        lazyLoad();
    }

    protected abstract void lazyLoad();

    protected void onInvisible() {
    }


    /**
     * 向用户动态申请权限
     *
     * @param requestCode
     * @param permissions
     */
    public void requestPermissions(int requestCode, String... permissions) {
        requestPermissions(permissions, requestCode);
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



    @Override
    public void onStop() {
        super.onStop();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
            unbinder = null;
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
        if (getContext() != null) {
            Toast.makeText(getContext(), toastMsg, Toast.LENGTH_SHORT).show();
        }
    }
}
