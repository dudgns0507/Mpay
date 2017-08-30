package io.github.dudgns0507.mpay.util;

import io.github.dudgns0507.mpay.models.Common;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Find {
    @FormUrlEncoded
    @POST("/find_user")
    Call<Common> find(@Field("query") String query, @Field("group_id") int group_id);
}
