package com.togo.home.data.model;

/**
 * Created by yangfeng on 17-3-21.
 */

public class BaseWrapper<T> {
    private int code;
    private T data;
    private String codeDesc;
    private String codeDescUser;
    private String codeName;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getCodeDesc() {
        return codeDesc;
    }

    public void setCodeDesc(String codeDesc) {
        this.codeDesc = codeDesc;
    }

    public String getCodeDescUser() {
        return codeDescUser;
    }

    public void setCodeDescUser(String codeDescUser) {
        this.codeDescUser = codeDescUser;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }
}
