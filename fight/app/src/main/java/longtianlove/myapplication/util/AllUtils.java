package longtianlove.myapplication.util;

import android.content.pm.Signature;

import longtianlove.bottomlib.util.AppUtil;
import longtianlove.myapplication.LongApplication;

/**
 * 项目独有的util
 * Created by 58 on 2016/12/13.
 */

public class AllUtils {
    /**
     * 获取签名的hash值
     * @return
     */
    public static String getAppSign(){
        Signature[] s= AppUtil.getAppSignature(LongApplication.mcontext);
        if(s==null)
            return null;
        return s[0].hashCode()+"";
    }
}
