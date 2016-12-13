package longtianlove.bottomlib.util;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

/**
 * Created by 58 on 2016/12/13.
 */

public class DevicesUtil {
    public static String imei;
    /**
     * 手机获取IMEI
     */
    public static String getIMEI(Context context) {
        if(!TextUtils.isEmpty(imei))return  imei;
        try{
            TelephonyManager tm = (TelephonyManager) context.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
            imei = tm.getDeviceId();
        }catch (Exception e){
        }
//        if(TextUtils.isEmpty(imei)||"000000000000000".equals(imei)||"0".equals(imei)){
//            imei = SPUtil.getImeikey();
//        }
//        if(TextUtils.isEmpty(imei)){
//            StringBuilder stringBuilder = new StringBuilder("35");
//            for(int i=0;i<13;i++){
//                stringBuilder.append(String.valueOf((int)(Math.random() * 10)));
//            }
//            SPUtil.putImeikey(stringBuilder.toString());
//        }
        return imei==null?"":imei;
    }

}
