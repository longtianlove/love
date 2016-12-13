package longtianlove.myapplication.login.presenter;

import android.widget.Toast;

import longtianlove.bottomlib.util.EncodeUtil;
import longtianlove.myapplication.LongApplication;
import longtianlove.myapplication.bussiness.ApiUtils;
import longtianlove.myapplication.login.ConstantsLogin;
import longtianlove.myapplication.login.bean.BaseUserBean;
import longtianlove.myapplication.login.view.LoginView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by long on 2016/12/12.
 */

public class LoginPresenter {
    LoginView mloginView;

    public LoginPresenter(LoginView loginView) {
        mloginView = loginView;
    }
    public void userLogin(String mobile,String pwd){
        String mobileEncode= EncodeUtil.encodeDes(mobile);
        String pwdEncode=EncodeUtil.encodeDes(pwd);
        ApiUtils.getPassportApiservice().userLogin(mobileEncode,pwdEncode, ConstantsLogin.JOBSOURCE+"").enqueue(new Callback<BaseUserBean>() {
            @Override
            public void onResponse(Call<BaseUserBean> call, Response<BaseUserBean> response) {
                Toast.makeText(LongApplication.mcontext,"成功",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<BaseUserBean> call, Throwable t) {
                Toast.makeText(LongApplication.mcontext,"失败",Toast.LENGTH_LONG).show();
            }
        });
    }
}
