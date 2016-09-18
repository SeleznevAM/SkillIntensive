package com.example.alex.skillintensive.data.network;

import com.example.alex.skillintensive.data.network.req.UserLoginReq;
import com.example.alex.skillintensive.data.network.resp.UserListRes;
import com.example.alex.skillintensive.data.network.resp.UserModelResp;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Класс для работы с сетью. Содержит в себе все необходимые методы
 */
public interface RestService {
    @POST("login")
    Call<UserModelResp> loginUser(@Body UserLoginReq req);

    @GET("user/list?orderBy=rating")
    Call<UserListRes> getUserList();

}
