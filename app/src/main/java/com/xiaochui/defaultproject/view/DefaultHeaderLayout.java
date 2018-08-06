package com.xiaochui.defaultproject.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.xiaochui.defaultproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Death丶Love
 * @project_name XiaoChuiProject
 * @package_name com.xiaochui.mainlibrary.customWidget
 * @time 2017/8/14 8:49
 * @remark
 */

public class DefaultHeaderLayout extends RelativeLayout {
    @BindView(R.id.default_headerLayout_backLayout)
    RelativeLayout defaultHeaderLayoutBackLayout;
    @BindView(R.id.default_headerLayout_titleLayout_title)
    TextView titleTv;
    @BindView(R.id.default_headerLayout_titleLayout)
    RelativeLayout backLayout;
    @BindView(R.id.default_headerLayout_rightLayoutIv)
    ImageView rightLayoutIv;
    @BindView(R.id.default_headerLayout_rightLayout)
    RelativeLayout rightLayout;
    @BindView(R.id.default_headerLayout_rightLayoutTv)
    TextView rightTv;
    private Context context;

    public DefaultHeaderLayout(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.default_layout_header, this);
        ButterKnife.bind(this);
    }

    public DefaultHeaderLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.default_layout_header, this);
        ButterKnife.bind(this);
    }


    public void setTitle(String title) {
        titleTv.setText(title);
    }

    public void setRightTv(String content) {
        rightTv.setText(content);
    }

    public void setOnBackClickListener(OnClickListener onClickListener) {
        defaultHeaderLayoutBackLayout.setOnClickListener(onClickListener);
    }

    public void setBackgroundColor(String color) {
        backLayout.setBackgroundColor(Color.parseColor("#e8423e"));
    }

    public void setRightLayoutClickListener(OnClickListener onClickListener) {
        rightLayout.setOnClickListener(onClickListener);
    }

    public void setRightLayoutIvBg(int mipmap) {
        rightLayoutIv.setBackgroundResource(mipmap);
    }

    public void setRightLayoutMargin(int margin) {
        LayoutParams params = ((LayoutParams) rightLayout.getLayoutParams());
        params.setMargins(0, 0, margin, 0);
        rightLayout.setLayoutParams(params);
    }

    public void setRightIvParams(int p) {
        LayoutParams params = ((LayoutParams) rightLayoutIv.getLayoutParams());
        params.width = p;
        params.height = p;
        rightLayoutIv.setLayoutParams(params);
    }

}
