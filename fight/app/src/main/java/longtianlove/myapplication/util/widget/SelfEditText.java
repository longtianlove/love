package longtianlove.myapplication.util.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.Toast;

import longtianlove.bottomlib.util.ToastUtil;
import longtianlove.myapplication.LongApplication;

/**
 * Created by 58 on 2016/12/22.
 */

public class SelfEditText extends EditText {
    public SelfEditText(Context context) {
        super(context);
    }

//    public SelfEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//    }

    public SelfEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SelfEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER){
            ToastUtil.showShortToastSafe(LongApplication.mcontext,"拦截拦截");
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }
}
