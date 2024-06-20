package com.ep.ai.hd.live.wallpaper.apiClient;


import static com.eventopackage.epads.utils.Cpp.ImagebaseApi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ep.ai.hd.live.wallpaper.utils.MyAppClass;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static RetrofitClient instance = null;
    private ApiClients api;

    public RetrofitClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(new AuthorizationToken(MyAppClass.appPrefrenceHelper.getLoginToken()))
//                .hostnameVerifier(new HostnameVerifier() {
//                    @Override
//                    public boolean verify(String hostname, SSLSession session) {
//                        HostnameVerifier hv = HttpsURLConnection.getDefaultHostnameVerifier();
//                        return hv.verify("web-hosting.com", session);
//                    }
//                })
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS);
        OkHttpClient okHttpClient = builder.build();

        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder().
                baseUrl(ImagebaseApi)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient).build();

        api = retrofit.create(ApiClients.class);
    }

    public synchronized static RetrofitClient getRetrofit() {
//        if (instance == null) {
        instance = new RetrofitClient();
//        }
        return instance;
    }

    public ApiClients getApi() {
        return api;
    }
}
