package longtianlove.bottomlib.network;

/**
 * Created by 58 on 2016/12/14.
 */

public class HttpCodeAnalyse {
    /**
     * 服务器返回常
     */
    public static boolean serverOK(int code){
        return  code>=200&&code<300?true:false;
    }

}
