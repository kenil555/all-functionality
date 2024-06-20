package com.ep.ai.hd.live.wallpaper.Repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.eventopackage.epads.utils.LogUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.ep.ai.hd.live.wallpaper.DataModel.ForgetResponse;
import com.ep.ai.hd.live.wallpaper.DataModel.LoginResponse;
import com.ep.ai.hd.live.wallpaper.DataModel.SigninResponse;
import com.ep.ai.hd.live.wallpaper.DataModel.UserProfileResponse;
import com.ep.ai.hd.live.wallpaper.apiClient.ApiClients;
import com.ep.ai.hd.live.wallpaper.apiClient.RetrofitClient;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    private static final String TAG = "UserRepository";
    public ApiClients apiClients;

    public UserRepository() {
        apiClients = RetrofitClient.getRetrofit().getApi();
    }

    public MutableLiveData<SigninResponse> registerUser(RequestBody requestBody) {
        MutableLiveData<SigninResponse> data = new MutableLiveData<>();
        apiClients.registerUser(requestBody).enqueue(new Callback<SigninResponse>() {
            @Override
            public void onResponse(Call<SigninResponse> call, Response<SigninResponse> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                    LogUtils.logE(TAG, "onResponse: getSingin -isSuccessful-----------> " + new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                } else {
                    try {
                        LogUtils.logE(TAG, " TRY  onResponse:getSingin Failed--------->" + response.errorBody().string());
                        String errorBodyString = response.errorBody().string();
                        SigninResponse errorResponse = new Gson().fromJson(errorBodyString, SigninResponse.class);
                        if (errorResponse == null) {
                            errorResponse = new SigninResponse();
                            errorResponse.setvMessage(errorResponse.getvMessage());
                            errorResponse.setiStatusCode(errorResponse.getiStatusCode());
                            errorResponse.setStatus(errorResponse.isStatus());
                        }
                        data.setValue(errorResponse);


                    } catch (IOException e) {
                        LogUtils.logE(TAG, " CATCH onResponse:getSingin Failed--------->");
                        SigninResponse errorResponse = new SigninResponse();
                        // Set appropriate error message
                        errorResponse.setvMessage("Error reading error response.");
                        // Set error response in LiveData
                        data.setValue(errorResponse);
                        throw new RuntimeException(e);

                    }


                }
            }

            @Override
            public void onFailure(Call<SigninResponse> call, Throwable t) {
                LogUtils.logE(TAG, "onFailure:getSingin --------------> " + t.toString());
            }
        });
        return data;
    }

    public MutableLiveData<LoginResponse> userLogin(RequestBody requestBody) {
        MutableLiveData<LoginResponse> data = new MutableLiveData<>();
        apiClients.userLogin(requestBody).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    LogUtils.logE(TAG, "onResponse: Login -isSuccessful-----------> " + new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                    data.setValue(response.body());
                } else {
                    try {

//                        String errorBodyLoginString = response.errorBody().string();
//                        LoginResponse errorResponse = new Gson().fromJson(errorBodyLoginString, LoginResponse.class);
//                        Log.e(TAG, "Converted Values-----------> " + new GsonBuilder().setPrettyPrinting().create().toJson(errorResponse));
//                        if (errorResponse == null) {
//                            errorResponse = new LoginResponse();
////                            errorResponse.setvMessage(errorResponse.getvMessage());
////                            errorResponse.setiStatusCode(errorResponse.getiStatusCode());
//                            Log.e(TAG, " TRY  onResponse:getSingin getvMessage--------->" + errorResponse.getiStatusCode());
//                            Log.e(TAG, " TRY  onResponse:getSingin getiStatusCode--------->" + errorResponse.getvMessage());
//                        }
                        data.setValue(null);
                        LogUtils.logE(TAG, " TRY  onResponse:getSingin Failed--------->" + response.errorBody().string());
                    } catch (IOException e) {
                        LogUtils.logE(TAG, " CATCH onResponse:getSingin Failed--------->");
                        LoginResponse errorResponse = new LoginResponse();
//                        errorResponse.setvMessage("Error reading error response.");
                        data.setValue(null);
                        throw new RuntimeException(e);

                    }

                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                LogUtils.logE(TAG, "onFailure:setSignUp --------------> " + t.toString());
            }
        });
        return data;
    }

    public MutableLiveData<UserProfileResponse> getUserProfile() {
        MutableLiveData<UserProfileResponse> data = new MutableLiveData<>();
        apiClients.getUserProfile().enqueue(new Callback<UserProfileResponse>() {
            @Override
            public void onResponse(Call<UserProfileResponse> call, Response<UserProfileResponse> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                    LogUtils.logE(TAG, "onResponse:getUserProfile SuccessFull--------->" + new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));

                } else {
                    String userdata = new Gson().toJson(response.body());
                    UserProfileResponse userProfileResponse = new Gson().fromJson(userdata, UserProfileResponse.class);

                    if (userProfileResponse != null) {
                        userProfileResponse.setiStatusCode(userProfileResponse.getiStatusCode());
                        userProfileResponse.setvMessage(userProfileResponse.getvMessage());
                    }
                    try {
                        LogUtils.logE(TAG, "onResponse:getUserProfile Failed--------->" + response.errorBody().string());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    data.setValue(userProfileResponse);
                }
            }

            @Override
            public void onFailure(Call<UserProfileResponse> call, Throwable t) {
                LogUtils.logE(TAG, "onFailure:getUserProfile --------------> " + t.toString());
                data.setValue(null);
            }
        });

        return data;
    }

    public MutableLiveData<JsonElement> setGoogleLogin(RequestBody requestBody) {
        MutableLiveData<JsonElement> data = new MutableLiveData<>();
        apiClients.googleLogin(requestBody).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                    LogUtils.logE(TAG, "Google Login SuccessFull--------->" + new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                    LogUtils.logE(TAG, "onResponse: Login is Successfull using Google");
                } else {
                    try {
                        LogUtils.logE(TAG, "Google Login  Failed--------->" + response.errorBody().string());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                LogUtils.logE(TAG, "onResponse: Login is Successfull using Google");
            }
        });
        return data;

    }

    public MutableLiveData<JsonElement> setUserLoginCount() {
        MutableLiveData<JsonElement> data = new MutableLiveData<>();
        apiClients.setUserCount().enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if (response.isSuccessful()) {
                    LogUtils.logE(TAG, "Google setUserLoginCount SuccessFull--------->" + new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                    LogUtils.logE(TAG, "onResponse: setUserLoginCount is Successfull ");
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                LogUtils.logE(TAG, "onResponse: setUserLoginCount is onFailure ");
            }
        });
        return data;

    }

    public MutableLiveData<JsonElement> setiViewCount(RequestBody requestBody) {
        MutableLiveData<JsonElement> data = new MutableLiveData<>();
        apiClients.setiViewCount(requestBody).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if (response.isSuccessful()) {
                    LogUtils.logE(TAG, "Google setiViewCount SuccessFull--------->" + new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                    LogUtils.logE(TAG, "onResponse: setiViewCount is Successfull using Google");
                } else {
                    try {
                        LogUtils.logE(TAG, "onResponse:setiViewCount Failed--------->" + response.errorBody().string());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                data.setValue(null);
                LogUtils.logE(TAG, "onFailure: setiViewCount is onFailure using Google");
            }
        });
        return data;

    }

    public MutableLiveData<JsonElement> setDownloadCount(RequestBody requestBody) {
        MutableLiveData<JsonElement> data = new MutableLiveData<>();
        apiClients.setDownloadCount(requestBody).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if (response.isSuccessful()) {
                    LogUtils.logE(TAG, "Google setiViewCount SuccessFull--------->" + new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                    LogUtils.logE(TAG, "onResponse: setiViewCount is Successfull using Google");
                } else {
                    try {
                        LogUtils.logE(TAG, "onResponse:setiViewCount Failed--------->" + response.errorBody().string());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                data.setValue(null);
                LogUtils.logE(TAG, "onFailure: setiViewCount is onFailure using Google");
            }
        });
        return data;

    }

    public MutableLiveData<JsonElement> setShareCount(RequestBody requestBody) {
        MutableLiveData<JsonElement> data = new MutableLiveData<>();
        apiClients.setShareCount(requestBody).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if (response.isSuccessful()) {
                    LogUtils.logE(TAG, "Google setiViewCount SuccessFull--------->" + new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                    LogUtils.logE(TAG, "onResponse: setiViewCount is Successfull using Google");
                } else {
                    try {
                        LogUtils.logE(TAG, "onResponse:setiViewCount Failed--------->" + response.errorBody().string());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                data.setValue(null);
                LogUtils.logE(TAG, "onFailure: setiViewCount is onFailure using Google");
            }
        });
        return data;

    }

    public MutableLiveData<JsonElement> upLoadImageRepo(String vCatId, ArrayList<String> arrayList, String vHashTage, MultipartBody.Part vImage, String vName) {
        MutableLiveData<JsonElement> data = new MutableLiveData<>();
        RequestBody vCatdBody = RequestBody.create(MediaType.parse("multipart/form-data"), vCatId);
        RequestBody vhashTagBody = RequestBody.create(MediaType.parse("multipart/form-data"), vHashTage);
        RequestBody vNameBody = RequestBody.create(MediaType.parse("multipart/form-data"), vName);
        ArrayList<RequestBody> colorIdsArray = new ArrayList<>();
        for (String name : arrayList) {
            RequestBody arrColorIdBody = RequestBody.create(MediaType.parse("multipart/form-data"), name);
            colorIdsArray.add(arrColorIdBody);
        }

        apiClients.uploadImage(vCatdBody, colorIdsArray, vhashTagBody, vImage, vNameBody).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                    LogUtils.logE(TAG, "onResponse:upLoadImageRepo SuccessFull--------->" + new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                } else {
                    try {
                        LogUtils.logE(TAG, "onResponse:upLoadImageRepo Failed--------->" + response.errorBody().string());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    data.setValue(null);

                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                LogUtils.logE(TAG, "onFailure:uploadImage --------------> " + t.toString());
                data.setValue(null);
            }
        });

        return data;
    }

    public MutableLiveData<JsonElement> upLoadReport(RequestBody vDescription, MultipartBody.Part vImage) {
        MutableLiveData<JsonElement> data = new MutableLiveData<>();
        apiClients.uploadReport(vDescription, vImage).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                    LogUtils.logE(TAG, "onResponse:upLoadReport SuccessFull--------->" + new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                } else {
                    try {
                        LogUtils.logE(TAG, "onResponse:upLoadReport Failed--------->" + response.errorBody().string());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    data.setValue(null);

                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                LogUtils.logE(TAG, "onFailure:upLoadReport --------------> " + t.toString());
                data.setValue(null);
            }
        });

        return data;
    }

    public MutableLiveData<JsonElement> ReportCotent(RequestBody requestBody) {
        MutableLiveData<JsonElement> data = new MutableLiveData<>();
        apiClients.reportContent(requestBody).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                    Log.e(TAG, "onResponse:upLoadImageRepo SuccessFull--------->" + new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                } else {
                    try {
                        Log.e(TAG, "onResponse:upLoadImageRepo Failed--------->" + response.errorBody().string());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    data.setValue(null);

                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.e(TAG, "onFailure:uploadImage --------------> " + t.toString());
                data.setValue(null);
            }
        });

        return data;
    }

    public MutableLiveData<ForgetResponse> forgotPassword(RequestBody requestBody) {
        MutableLiveData<ForgetResponse> data = new MutableLiveData<>();
        apiClients.forgotPassword(requestBody).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                ForgetResponse<JsonElement> apiResponse;
                if (response.isSuccessful()) {
                    Log.e(TAG, "onResponse:forgotPassword SuccessFull--------->" + new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                    apiResponse = new ForgetResponse<>(response.code(), true, response.body(), "Success");
                } else {
                    try {
                        Log.e(TAG, "onResponse:forgotPassword Failed--------->" + response.errorBody().string());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    apiResponse = new ForgetResponse<>(response.code(), true, null, "Email or mobile is incorrect");
                }
                data.setValue(apiResponse);
                if (response.isSuccessful()) {
                    Log.e(TAG, "onResponse:forgotPassword SuccessFull--------->" + new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                } else {
                    try {
                        Log.e(TAG, "onResponse:forgotPassword Failed--------->" + response.errorBody().string());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.e(TAG, "onFailure:forgotPassword --------------> " + t.toString());
                data.setValue(null);
            }
        });

        return data;
    }

//    public MutableLiveData<JsonElement> upLoadImageRepo2(String vCatId, ArrayList<String> arrayList, String vHashTage, MultipartBody.Part vImage, String vName) {
//        MutableLiveData<JsonElement> data = new MutableLiveData<>();
//        RequestBody vCatdBody = RequestBody.create(MediaType.parse("multipart/form-data"), vCatId);
//        RequestBody vhashTagBody = RequestBody.create(MediaType.parse("multipart/form-data"), vHashTage);
//        RequestBody vNameBody = RequestBody.create(MediaType.parse("multipart/form-data"), vName);
//
//        ArrayList<RequestBody> allIdsArray = new ArrayList<>();
//        for (String name : arrayList) {
//            Log.e(TAG, "upLoadImageRepo2: -ids-------> "+name );
//            RequestBody arrColorIdBody = RequestBody.create(MediaType.parse("text/plain"), name);
//            allIdsArray.add(arrColorIdBody);
//        }
//
//
//        apiClients.uploadImage2(vCatdBody, allIdsArray, vhashTagBody, vImage, vNameBody).enqueue(new Callback<JsonElement>() {
//            @Override
//            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
//                if (response.isSuccessful()) {
//                    data.setValue(response.body());
//                    Log.e(TAG, "onResponse:upLoadImageRepo SuccessFull--------->" + new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
//                } else {
//                    try {
//                        Log.e(TAG, "onResponse:upLoadImageRepo Failed--------->" + response.errorBody().string());
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
//                    data.setValue(response.body());
//
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<JsonElement> call, Throwable t) {
//                Log.e(TAG, "onFailure:uploadImage --------------> " + t.toString());
//                data.setValue(null);
//            }
//        });
//
//        return data;
//    }


}
