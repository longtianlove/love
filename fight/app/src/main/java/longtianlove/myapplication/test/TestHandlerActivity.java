package longtianlove.myapplication.test;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import longtianlove.myapplication.LongApplication;
import longtianlove.myapplication.R;

/**
 * Created by 58 on 2016/12/15.
 */

public class TestHandlerActivity extends Activity implements View.OnClickListener {
    TextView tv_show;
    Button bt_go;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testhandler);
        initView();
        initListener();
        toastInOtherThread();
    }
    void initView(){
        tv_show= (TextView) this.findViewById(R.id.tv_show);
        bt_go= (Button) this.findViewById(R.id.bt_go);
    }
    void initListener(){
        bt_go.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_go:
//                toastInOtherThread();
//                toastInOtherThreadAfterSleep();
//                mainHandlerToast();
                otherThreadHandler();

                break;

        }
    }
    /**
     * 在子线程中弹toast
     */
    void toastInOtherThread(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                tv_show.setText("handler之后");
            }
        }).start();
    }

    /**
     * 子线程sleep之后，弹toast
     *
     * checkThread()
     * Only the original thread that created a view hierarchy can touch its views.
     */
    void toastInOtherThreadAfterSleep(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                tv_show.setText("handler之后");
            }
        }).start();
    }
    /**
     * 主线程去跑
     */
    void mainHandlerToast(){
    new Handler().post(new Runnable() {
        @Override
        public void run() {
            tv_show.setText("handler之后");
        }
    });
    }
    /**
     * 子线程通过handler
     */
    void otherThreadHandler(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                Handler handler=new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
//                        tv_show.setText("handler之后");
                        Toast.makeText(LongApplication.mcontext,"handler之后",Toast.LENGTH_LONG).show();

                    }
                });
                Looper.loop();

            }
        }).start();
    }
}
