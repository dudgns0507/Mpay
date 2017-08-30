package io.github.dudgns0507.mpay.util;

import io.github.dudgns0507.mpay.models.Common;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Conclude {
    @FormUrlEncoded
    @POST("/conclude_event")
    Call<Common> conclude(@Field("event_id") int event_id);
}
