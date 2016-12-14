package longtianlove.bottomlib.network;

import okhttp3.OkHttpClient;

/**
 * Created by 58 on 2016/12/14.
 */

public class HttpSwitch {
    public enum State{
        HTTP,
        OFFLINE_HTTPS,
        ONLINE_HTTPS
    }
 static volatile  State state=State.OFFLINE_HTTPS;

    public static void toOffline(OkHttpClient.Builder builder){
        if(state==State.OFFLINE_HTTPS) {
            //如果是线下，信任所有证书
            builder.sslSocketFactory(SSLFactory.createSSLSocketFactory());
            builder.hostnameVerifier(new SSLFactory.TrustAllHostnameVerifier());
        }
    }
}
