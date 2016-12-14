package longtianlove.bottomlib.network;

import longtianlove.bottomlib.base.BaseCommonNet;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 *网络集体拦截器
 */

public abstract class NetWorkCallBack <T extends BaseCommonNet> implements Callback<T>{

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        T netBody=response.body();
        if(netBody==null)
            onFail(call,null);
        if(HttpCodeAnalyse.serverOK(response.code())){
            onSuccess(response,netBody);
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        onFail(call,t);
    }

    public abstract void onSuccess(Response<T> response, T netBody);
    public abstract void onFail(Call<T> call,Throwable t);
}
