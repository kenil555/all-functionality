package com.ep.ai.hd.live.wallpaper.Repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.eventopackage.epads.utils.LogUtils;
import com.google.gson.GsonBuilder;
import com.ep.ai.hd.live.wallpaper.DataModel.CategoryResponse;
import com.ep.ai.hd.live.wallpaper.apiClient.ApiClients;
import com.ep.ai.hd.live.wallpaper.apiClient.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryRepository {
    private static final String TAG = "CategoryRepository";
    public ApiClients apiClients;

    public CategoryRepository() {
        this.apiClients = RetrofitClient.getRetrofit().getApi();
    }
    public MutableLiveData<CategoryResponse> getColorDetails()
    {
        MutableLiveData<CategoryResponse> data = new MutableLiveData<>();
        apiClients.getColorDetails().enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if(response.isSuccessful())
                {
                    data.setValue(response.body());
                    LogUtils.logE(TAG, "isSuccessful: getColorDetails--------> "+new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                }else {
                    data.setValue(response.body());
                    LogUtils.logE(TAG, "onFailed: getColorDetails--------> "+response.errorBody().toString() );
                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                data.setValue(null);
                LogUtils.logE(TAG, "onFailure: getColorDetails--------> "+t );
            }
        });
        return data;
    }
    public MutableLiveData<CategoryResponse> getCategoryData()
    {
        MutableLiveData<CategoryResponse> data = new MutableLiveData<>();
        apiClients.getCategory().enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if(response.isSuccessful())
                {
                    data.setValue(response.body());
//                    Log.e(TAG, "isSuccessful: getCategoryData--------> "+new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                }else {
                    data.setValue(response.body());
                    LogUtils.logE(TAG, "onFailed: getCategoryData--------> "+response.errorBody().toString() );
                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                data.setValue(null);
                LogUtils.logE(TAG, "onFailure: getCategoryData--------> "+t );
            }
        });
        return data;
    }
}
