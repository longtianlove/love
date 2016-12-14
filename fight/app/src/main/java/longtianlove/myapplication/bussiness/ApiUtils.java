package longtianlove.myapplication.bussiness;

import android.text.TextUtils;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;

import longtianlove.bottomlib.network.HttpSwitch;
import longtianlove.bottomlib.util.AppUtil;
import longtianlove.bottomlib.util.DevicesUtil;
import longtianlove.myapplication.Constants;
import longtianlove.myapplication.LongApplication;
import longtianlove.myapplication.login.UserInfoModel;
import longtianlove.myapplication.util.AllUtils;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by long on 2016/12/13.
 * Retrofit retrofit=new Retrofit.Builder().baseurl("https://api.github.com").build();
 * ApiService apiservice=retrofit.create(ApiService.class);
 */

public class ApiUtils {
    public static final String HOST = "https://passport.chinahr.com/app/";
    public static ApiService passportApiservice;


    private static int TIME_OUT_CONNECT = 10;//连接超时时间
    private static int TIME_OUT_READ = 30;//读取超时时间
    private static int TIME_OUT_WRITE = 120;//写入超时时间

    public static ApiService getPassportApiservice() {
        if (passportApiservice == null) {
            synchronized (ApiService.class) {
                if (passportApiservice == null) {
                    passportApiservice=getRetrofit().create(ApiService.class);
                }
            }
        }
        return passportApiservice;
    }

    public static Retrofit getRetrofit() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();//设置log过滤器
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        Interceptor headerInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();//获取原始request
                Request.Builder requestBuilder=originalRequest.newBuilder() //添加统一的header
                        .header("versionCode", "Android_" + AppUtil.getAppVersionCode(LongApplication.mcontext))
                        .header("versionName", "Android_" + AppUtil.getAppVersionName(LongApplication.mcontext))
                        .header("UMengChannel", Constants.UMengChannel)
                        .header("uid", UserInfoModel.getInstannce().baseUserBean.getuId()) //.header("uid", SPUtil.getUId())
                        .header("Cookie", UserInfoModel.getInstannce().baseUserBean.getCookie())// .header("Cookie", SelectClientInstance.getSelectClientInstance().isSelectJobClient() ? "PPS=" + SPUtil.getPPS() : "bps=" + SPUtil.getBps())
                        .header("appSign", AllUtils.getAppSign())
                        .header("deviceID", DevicesUtil.getIMEI(LongApplication.mcontext))
                        .header("deviceName", URLEncoder.encode(android.os.Build.MODEL.replaceAll(" ", ""), "UTF-8"))
                        .header("role", UserInfoModel.getInstannce().role.toString())
                        .header("deviceModel", URLEncoder.encode(android.os.Build.MODEL.replaceAll(" ", ""), "UTF-8"))//备注okhttp不支持追传汉字，需要编码下。
                        .header("deviceVersion", android.os.Build.VERSION.RELEASE)
                        .header("pushVersion", "52")
                        .header("platform","Android-"+android.os.Build.VERSION.SDK_INT)
                        .header("User-Agent", "ChinaHrAndroid"+AppUtil.getAppVersionName(LongApplication.mcontext))
                        .header("extion", TextUtils.isEmpty(Constants.API_EXTION) ? "" : Constants.API_EXTION)
//                        .header("pbi", PBINetCollection.getCollection())
                        .header("Brand", URLEncoder.encode(android.os.Build.BRAND, "UTF-8"))//备注okhttp不支持追传汉子，需要编码下。
                        .header("device_id", DevicesUtil.getIMEI(LongApplication.mcontext))
                        .header("device_os", "Android")
                        .header("device_os_version",android.os.Build.VERSION.RELEASE)
                        .header("app_version", AppUtil.getAppVersionName(LongApplication.mcontext))
                        .header("uidrole", UserInfoModel.getInstannce().role.toString())
                        .header("utm_source",Constants.UMengChannel)
//                        .header("tinkerLoadedSuccess", Tinker.with(ChrApplication.getContext()).isTinkerLoaded()+"")//标示Tinker是否成功加载。
                        .method(originalRequest.method(),originalRequest.body());

                Request request=requestBuilder.build();
                return chain.proceed(request);
            }
        };
        Retrofit retrofit;
        OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
        OkHttpClient httpClient;
        httpBuilder.connectTimeout(TIME_OUT_CONNECT, TimeUnit.SECONDS)//超时时间
                .writeTimeout(TIME_OUT_WRITE, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT_READ, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)//重试机制
                .addInterceptor(headerInterceptor)//请求拦截器
                .addInterceptor(loggingInterceptor);//Log拦截器
        HttpSwitch.toOffline(httpBuilder);
        httpClient = httpBuilder.build();
        retrofit = new Retrofit.Builder()
                .baseUrl(HOST)
                .addConverterFactory(FastJsonConverterFactory.create())//使用Gson解析数据。
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//添加对RxJava的支持
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(httpClient)//添加统一的Header和打印Logger的时候需要使用过滤器，需要使用到OKHttpClient。必须要注意添加的方式，不同版本（早期版本）不一样。
                .build();
        return retrofit;
    }
}
