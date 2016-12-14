package longtianlove.myapplication.login.presenter;

import android.widget.Toast;

import longtianlove.bottomlib.network.NetWorkCallBack;
import longtianlove.bottomlib.util.EncodeUtil;
import longtianlove.bottomlib.util.ToastUtil;
import longtianlove.myapplication.Constants;
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
        ToastUtil.showShortToastInOtherThread(LongApplication.mcontext,"");

        String mobileEncode= EncodeUtil.encodeDes(mobile);
        String pwdEncode=EncodeUtil.encodeDes(pwd);
        ApiUtils.getPassportApiservice().userLogin(mobileEncode,pwdEncode, ConstantsLogin.JOBSOURCE+"").enqueue(new NetWorkCallBack<BaseUserBean>() {
            @Override
            public void onSuccess(Response<BaseUserBean> response, BaseUserBean netBody) {
                if(netBody.code== Constants.CODE_OK){
                    ToastUtil.showShortToastSafe(LongApplication.mcontext,netBody.msg);
                }
            }

            @Override
            public void onFail(Call<BaseUserBean> call, Throwable t) {
                ToastUtil.showShortToastSafe(LongApplication.mcontext,"网络失败");
            }
        });
    }
}
