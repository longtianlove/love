package longtianlove.bottomlib.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

/**
 * Created by 58 on 2016/12/14.
 */

public class ToastUtil {
    private static Handler mhandler=new Handler(Looper.getMainLooper());
    private static Toast mtoast;
    /**
     * 安全的弹toast
     */
    public static void showShortToastSafe(final Context context, final CharSequence text){
        mhandler.post(new Runnable() {
            @Override
            public void run() {
                if(mtoast==null){
                    mtoast=Toast.makeText(context,text,Toast.LENGTH_SHORT);
                }else{
                    mtoast.setText(text);
                    mtoast.setDuration(Toast.LENGTH_SHORT);
                }
                mtoast.show();
            }
        });
    }

    /**
     * 子线程弹toast
     */
    public static void showShortToastInOtherThread(final Context context, final CharSequence text){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Looper.prepare();
                Toast.makeText(context,"text",Toast.LENGTH_LONG).show();
//            }
//        }).start();
    }
    /**
     * 取消吐司显示
     */
    public static void cancelToast() {
        if (mtoast != null) {
            mtoast.cancel();
            mtoast = null;
        }
    }
}
