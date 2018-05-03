package NetAsyncTask;

import java.io.Serializable;

public class ResponseObject implements Serializable{

    /**
     * errorCode : 200
     * msg : 登录成功
     * content : {"accesstoken":"b38f18d3ebbb17da30be80ff4ebcadcd"}
     * success : false
     */

    private int errorCode;
    private String msg;
    private Object content;
    private boolean success;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}

