package longtianlove.myapplication.login;

import java.util.List;

import longtianlove.myapplication.login.bean.BaseUserBean;

/**
 * Created by 58 on 2016/12/13.
 */

public class UserInfoModel {
    private static UserInfoModel instance=new UserInfoModel();
    private UserInfoModel(){
    }
    public static UserInfoModel getInstannce(){
        return instance;
    }
    public enum Role{
        /**
         * 没有选择角色
         */
        NONE("null"),
        /**
         * B 端用户
         */
        BOSS("boss"),
        /**
         * C端用户
         */
        CLIENT("client");
        private final String role;
        Role(String role){
            this.role=role;
        }
        @Override public String toString() {
            return role;
        }
    }
    public volatile Role role=Role.CLIENT;
    public void setRole(Role role){
        if(role==null)
            return;
        this.role=role;
    }
    //用户基本信息
    public BaseUserBean baseUserBean=new BaseUserBean();




    public void setbaseUserBean(){

    }

    /**
     * OKhttp获取cookie
     * @param cookieList 获取okhttp中的list
     * @param cookieName 获取cookie的名字
     * @return cookie
     */
    public  String getOKHttpCookie(List<String> cookieList, String cookieName){
        String cookie="";
        List<String> cookies=cookieList;
        if(cookies==null){
            return cookie;
        }
        for(String cookieItem:cookies) {
            int start = -1;
            if ((start = cookieItem.indexOf(cookieName)) >= 0) {
                start=cookieName.length()+1;//去除引号+1
                int end = cookieItem.indexOf("; ")-1;//去除引号-1
                cookie = cookieItem.substring(start, end);
            }
        }
        return  cookie;
    }

}
