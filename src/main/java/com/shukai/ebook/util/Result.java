package com.shukai.ebook.util;


public class Result {
    private String message;
    private Object object;

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    private final static Result instance=new Result();
    public static Result getInstance(){
        return instance;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
