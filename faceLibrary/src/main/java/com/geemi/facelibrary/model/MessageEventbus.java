package com.geemi.facelibrary.model;

public class MessageEventbus {
    private String key;
    private Object object;
    private int messageId;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    @Override
    public String toString() {
        return "MessageEventbus{" +
                "key='" + key + '\'' +
                ", object=" + object +
                ", messageId=" + messageId +
                '}';
    }
}
