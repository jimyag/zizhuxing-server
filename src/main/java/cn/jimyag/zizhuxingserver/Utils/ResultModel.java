package cn.jimyag.zizhuxingserver.Utils;

import java.io.Serializable;

public class ResultModel implements Serializable {

    private int code;
    private String msg;
    private Object data;

    public ResultModel() {
        data = "";
    }

    public ResultModel(int error_code, String msg, Object data) {
        this.code = error_code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
