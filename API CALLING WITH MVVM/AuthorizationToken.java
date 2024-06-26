package com.ep.ai.hd.live.wallpaper.apiClient;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthorizationToken implements Interceptor {
    String token ;
    public AuthorizationToken(String token) {
        this.token = token;
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request().newBuilder().addHeader("Authorization","Bearer "+ token).build();
        return chain.proceed(request);
    }
}
