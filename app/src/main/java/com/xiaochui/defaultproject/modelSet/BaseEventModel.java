package com.xiaochui.defaultproject.modelSet;

/**
 * @author Death丶Love
 * @project_name XiaoChuiProject
 * @package_name com.xiaochui.mainlibrary.dataModelSet
 * @time 2017/11/1 10:11
 * @remark
 */

public class BaseEventModel {
    public static final int NewMessage = 1;
    private EventType type;
    private String remark;
    private Object obj;

    public BaseEventModel() {
    }

    public BaseEventModel(EventType type, String remark, Object obj) {
        this.type = type;
        this.remark = remark;
        this.obj = obj;
    }

    public static int getNewMessage() {
        return NewMessage;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public enum EventType {
        CUSTOMJPUSHMESSAGE(301);

        int value;

        EventType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
