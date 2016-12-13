package longtianlove.myapplication.bussiness;

import longtianlove.myapplication.login.bean.BaseUserBean;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by 龙 on 2016/12/12.
 */

public interface ApiService {
    @FormUrlEncoded
    @POST("login")
    Call<BaseUserBean> userLogin(@Field("input") String input, @Field("pwd") String pwd, @Field("source") String source);
}
