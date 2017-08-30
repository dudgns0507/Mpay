package io.github.dudgns0507.mpay.util;

import io.github.dudgns0507.mpay.models.Common;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Pay {
    @FormUrlEncoded
    @POST("/pay")
    Call<Common> pay(@Field("event_id") int event_id, @Field("user_id") int user_id, @Field("pay") int pay);
}
