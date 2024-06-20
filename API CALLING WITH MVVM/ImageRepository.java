package com.ep.ai.hd.live.wallpaper.Repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.eventopackage.epads.utils.LogUtils;
import com.google.gson.GsonBuilder;
import com.ep.ai.hd.live.wallpaper.DataModel.ListPostResponse;
import com.ep.ai.hd.live.wallpaper.DataModel.PopularResponse;
import com.ep.ai.hd.live.wallpaper.apiClient.ApiClients;
import com.ep.ai.hd.live.wallpaper.apiClient.RetrofitClient;

import java.io.IOException;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageRepository {
    private static final String TAG = "ImageRepository";
    public ApiClients apiClients;

    public ImageRepository() {
        apiClients = RetrofitClient.getRetrofit().getApi();
    }

    public MutableLiveData<ListPostResponse> getPostListById(RequestBody requestBody) {
        MutableLiveData<ListPostResponse> data = new MutableLiveData<>();
        apiClients.getPostList(requestBody).enqueue(new Callback<ListPostResponse>() {
            @Override
            public void onResponse(Call<ListPostResponse> call, Response<ListPostResponse> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                    LogUtils.logE(TAG, "isSuccessful: getPostListById--------> " + new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                } else {
                    try {
                        LogUtils.logE(TAG, "onResponse:getPostListById Failed--------->" + response.errorBody().string());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ListPostResponse> call, Throwable t) {
                data.setValue(null);
                LogUtils.logE(TAG, "onFailure: getPostListById--------> " + t);
            }
        });

        return data;
    }

    public MutableLiveData<PopularResponse> getPopularImages() {
        MutableLiveData<PopularResponse> data = new MutableLiveData<>();
        apiClients.getPopular().enqueue(new Callback<PopularResponse>() {
            @Override
            public void onResponse(Call<PopularResponse> call, Response<PopularResponse> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
//                    Log.e(TAG, "isSuccessful: getPopularImages--------> " + new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                } else {
                    try {
                        LogUtils.logE(TAG, "onResponse:getPopularImages Failed--------->" + response.errorBody().string());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<PopularResponse> call, Throwable t) {
                data.setValue(null);
                LogUtils.logE(TAG, "onFailure: getPopularImages--------> " + t);
            }
        });

        return data;
    }
    public MutableLiveData<PopularResponse> getPremiumImages() {
        MutableLiveData<PopularResponse> data = new MutableLiveData<>();
        apiClients.getPremium().enqueue(new Callback<PopularResponse>() {
            @Override
            public void onResponse(Call<PopularResponse> call, Response<PopularResponse> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                    LogUtils.logE(TAG, "isSuccessful: getPremiumImages--------> " + new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                } else {
                    try {
                        LogUtils.logE(TAG, "onResponse:getPremiumImages Failed--------->" + response.errorBody().string());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<PopularResponse> call, Throwable t) {
                data.setValue(null);
                LogUtils.logE(TAG, "onFailure: getPremiumImages--------> " + t);
            }
        });

        return data;
    }
}
