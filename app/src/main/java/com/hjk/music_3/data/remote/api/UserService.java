package com.hjk.music_3.data.remote.api;

import com.hjk.music_3.data.local.model.Music;
import com.hjk.music_3.data.local.model.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserService {

    @GET("user_list")
    Call<List<User>> getUser();

    @GET("user_list/{id}")
    Call<User> getLogin(@Path("id") String id);

    @FormUrlEncoded
    @POST("user_insert")
    Call<ResponseBody> sign(@Field("objJson") String objJson);

    @FormUrlEncoded
    @POST("user_update")
    Call<ResponseBody> pass_change(@Field("objJson") String objJson);
}
