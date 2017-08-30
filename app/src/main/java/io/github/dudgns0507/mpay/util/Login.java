package io.github.dudgns0507.mpay.util;

import io.github.dudgns0507.mpay.models.Common;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Login {
    @FormUrlEncoded
    @POST("/signin")
    Call<Common> login(@Field("email") String email, @Field("passwd") String passwd, @Field("token") String token);
}
