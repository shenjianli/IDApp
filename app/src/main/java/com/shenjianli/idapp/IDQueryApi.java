package com.shenjianli.idapp;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;


/**
 * Created by edianzu on 2017/2/15.
 */
public interface IDQueryApi {

    @GET("idcard/index")
    Call<IDData> queryIdData(@QueryMap Map<String, String> parmas, @Query("key") String key);
}
