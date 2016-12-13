package longtianlove.myapplication.login.bean;

import longtianlove.bottomlib.base.BaseCommonNet;

/**
 * Created by 58 on 2016/12/12.
 */

public class BaseUserBean extends BaseCommonNet<BaseUserBean> {
    public String getCookie() {
        return cookie==null?"":cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getNewImToken() {
        return newImToken;
    }

    public void setNewImToken(String newImToken) {
        this.newImToken = newImToken;
    }

    public String getuId() {
        return uId==null?"":uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    //    public String uid = "";
//    public int infoFlag;
//    public String imToken;
//    public String isNew="";
//    public String token = "";
//    public String newImToken = "";
    public String uId;
    public String userMobile;
    public String uName;
    public String newImToken;
    public String cookie;
}
