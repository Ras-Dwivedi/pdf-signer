package com.crubn.ssi.signer;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Message implements Serializable {
    private static final long serialVersionUID = 129348938L;
    private String msg;

    @JsonProperty("msg")
    public String getMsg() {
        return msg;
    }

    @JsonProperty("msg")
    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Message(String msg) {
        this.msg = msg;
    }
    public Message(){
        this.msg = "some message";
    }
}
