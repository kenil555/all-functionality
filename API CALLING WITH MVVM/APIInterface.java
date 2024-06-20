package com.ep.ai.hd.live.wallpaper.apiClient;

import com.ep.ai.hd.live.wallpaper.apiClient.model.category.CategoryMainJson;
import com.ep.ai.hd.live.wallpaper.apiClient.model.categoryList.CategoryListMainJson;
import com.ep.ai.hd.live.wallpaper.apiClient.model.color.ColorMainJson;
import com.ep.ai.hd.live.wallpaper.apiClient.model.colorList.ColorListMainJson;
import com.ep.ai.hd.live.wallpaper.apiClient.model.splash.SplashMainJson;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIInterface {
    @FormUrlEncoded
    @POST("/Wallpaper/4k Wallpaper/api/splash.php")
    Call<SplashMainJson> splashList(@Field("CLIENT_ID") String cliendId,
                                    @Field("DEVICE_KEY") String deviceKey,
                                    @Field("APPS_KEY") String appsKey,
                                    @Field("APPS_VER_KEY") String appverKey);

    @FormUrlEncoded
    @POST("/Wallpaper/4k Wallpaper/api/category.php")
    Call<CategoryMainJson> categoryList(@Field("CLIENT_ID") String cliendId,
                                        @Field("APPSAUTH") String appAuth,
                                        @Field("DATAOFFSET") int dataOffset);

    @FormUrlEncoded
    @POST("/Wallpaper/4k Wallpaper/api/color.php")
    Call<ColorMainJson> colorList(@Field("CLIENT_ID") String cliendId,
                                  @Field("APPSAUTH") String appAuth,
                                  @Field("DATAOFFSET") int dataOffset);

    @FormUrlEncoded
    @POST("/Wallpaper/4k Wallpaper/api/categoryList.php")
    Call<CategoryListMainJson> categoryImageList(@Field("CLIENT_ID") String cliendId,
                                                 @Field("APPSAUTH") String appAuth,
                                                 @Field("CATEGORYID") String categoryId,
                                                 @Field("DATAOFFSET") int dataOffset);

    @FormUrlEncoded
    @POST("/Wallpaper/4k Wallpaper/api/colorList.php")
    Call<ColorListMainJson> colorImageList(@Field("CLIENT_ID") String cliendId,
                                           @Field("APPSAUTH") String appAuth,
                                           @Field("COLODID") String categoryId,
                                           @Field("DATAOFFSET") int dataOffset);
}
