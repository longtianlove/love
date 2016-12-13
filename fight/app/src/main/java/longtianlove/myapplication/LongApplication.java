package longtianlove.myapplication;

import android.app.Application;
import android.content.Context;

/**
 * Created by 58 on 2016/12/13.
 */

public class LongApplication extends Application {
    public static Context mcontext;

    @Override
    public void onCreate() {
        super.onCreate();
        mcontext=getApplicationContext();
    }
}
