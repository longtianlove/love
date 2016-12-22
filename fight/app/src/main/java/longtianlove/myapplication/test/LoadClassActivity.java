package longtianlove.myapplication.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import longtianlove.bottomlib.util.ToastUtil;
import longtianlove.myapplication.LongApplication;
import longtianlove.myapplication.R;
import longtianlove.myapplication.util.LoadClassUtil;

/**
 * Created by 58 on 2016/12/22.
 */

public class LoadClassActivity extends Activity {
    EditText input;
    View tv_load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loadclass);
        input = (EditText) this.findViewById(R.id.input);
        tv_load = this.findViewById(R.id.tv_load);
        tv_load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });

    }

    void search() {
        String text = input.getText().toString();
        List<String> resultList = LoadClassUtil.getClasses(LongApplication.mcontext);
        for (String name : resultList) {
//         if(name.contains(text)){
             ToastUtil.showShortToastSafe(this,name+"----存在");
//         }
        }
        ToastUtil.showShortToastSafe(this,"扫描结束");

    }
//    void loadClass(){
//        String text = input.getText().toString();
//        ClassLoader cl=LongApplication.mcontext.getClassLoader();
//        Class orderClass=cl.loadClass(text);
//    }
}
