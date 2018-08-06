package com.xiaochui.defaultproject.modelSet;

import java.util.List;

/**
 * @author Death丶Love
 * @project_name XiaoChuiProject
 * @package_name com.xiaochui.mainlibrary.dataModelSet
 * @time 2017/10/16 17:36
 * @remark
 */

public class FunctionParamesModel {

    /**
     * className : com.bocai.czeducation.activities.ExamActivity
     * paramsList : [{"k":"questionId","v":"needId"}]
     */
    public static final String BGTRANSPARENCY = "bgTransparency";
    private String className;
    private List<ParamsListBean> paramsList;
    private int bgTransparency;
    private int closeWebActivity;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<ParamsListBean> getParamsList() {
        return paramsList;
    }

    public void setParamsList(List<ParamsListBean> paramsList) {
        this.paramsList = paramsList;
    }

    public int getBgTransparency() {
        return bgTransparency;
    }

    public void setBgTransparency(int bgTransparency) {
        this.bgTransparency = bgTransparency;
    }

    public int getCloseWebActivity() {
        return closeWebActivity;
    }

    public void setCloseWebActivity(int closeWebActivity) {
        this.closeWebActivity = closeWebActivity;
    }

    public static class ParamsListBean {
        /**
         * k : questionId
         * v : needId
         */

        private String k;
        private String v;

        public String getK() {
            return k;
        }

        public void setK(String k) {
            this.k = k;
        }

        public String getV() {
            return v;
        }

        public void setV(String v) {
            this.v = v;
        }
    }
}
