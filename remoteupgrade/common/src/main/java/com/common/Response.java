package com.common;

import java.io.Serializable;

/**
 * @ClassName Response
 * @Description TODO
 * @Author xizhonghuai
 * @Date 2019-04-30 12:00
 * @Version 1.0
 */
public class Response implements  Serializable{

    private Integer statusCode;
    private String reason;
    private Object result;
    private Object spareParam;

    public Response() {
        this.statusCode = 200;
        this.reason = "success";
    }

    public Response(Object result) {
        this.result = result;
        this.statusCode = 200;
        this.reason = "success";

    }

    public Response(Object result, Object spareParam) {
        this.result = result;
        this.spareParam = spareParam;
        this.statusCode = 200;
        this.reason = "success";
    }

    public Response(Integer statusCode, String reason) {
        this.statusCode = statusCode;
        this.reason = reason;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Object getSpareParam() {
        return spareParam;
    }

    public void setSpareParam(Object spareParam) {
        this.spareParam = spareParam;
    }

    @Override
    public String toString() {
        return "Response{" +
                "statusCode=" + statusCode +
                ", reason='" + reason + '\'' +
                ", result=" + result +
                ", spareParam=" + spareParam +
                '}';
    }
}
