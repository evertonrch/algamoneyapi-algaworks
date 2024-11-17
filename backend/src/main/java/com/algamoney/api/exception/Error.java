package com.algamoney.api.exception;

public class Error {

    private String msg;
    private String msgDesenvolvedor;

    public Error(String msg, String msgDesenvolvedor) {
        this.msg = msg;
        this.msgDesenvolvedor = msgDesenvolvedor;
    }

    public String getMsg() {
        return msg;
    }

    public String getMsgDesenvolvedor() {
        return msgDesenvolvedor;
    }
}