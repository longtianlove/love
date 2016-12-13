package longtianlove.bottomlib.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.text.TextUtils;

/**
 * app相关工具类
 */

public class AppUtil {

    /**
     * 获取App版本号
     *
     * @param context 上下文
     * @return App版本号
     */
    public static String getAppVersionName(Context context) {
        return getAppVersionName(context, context.getPackageName());
    }

    /**
     * @param context     上下文
     * @param packageName 包名
     * @return 返回版本名称
     */
    public static String getAppVersionName(Context context, String packageName) {
        if (TextUtils.isEmpty(packageName))
            return null;
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int getAppVersionCode(Context context) {
        return getAppVersionCode(context, context.getPackageName());
    }

    public static int getAppVersionCode(Context context, String packageName) {
        if (TextUtils.isEmpty(packageName))
            return -1;
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? -1 : pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }
    /**
     * 获取app签名
     */
    public static Signature[] getAppSignature(Context context){
        return getAppSignature(context,context.getPackageName());
    }
    public static Signature[] getAppSignature(Context context,String packageName){
        if(TextUtils.isEmpty(packageName))
            return null;
        PackageManager pm=context.getPackageManager();
        try {
            PackageInfo pi=pm.getPackageInfo(packageName,PackageManager.GET_SIGNATURES);
            return pi==null?null:pi.signatures;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

}
