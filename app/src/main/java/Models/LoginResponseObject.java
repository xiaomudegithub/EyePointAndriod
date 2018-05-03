package Models;

import java.io.Serializable;

public class LoginResponseObject implements Serializable {

    /**
     * accesstoken : 34f59e448abcade0f8e5b569981d660e
     */

    private String accesstoken;

    public String getAccesstoken() {
        return accesstoken;
    }

    public void setAccesstoken(String accesstoken) {
        this.accesstoken = accesstoken;
    }
}
