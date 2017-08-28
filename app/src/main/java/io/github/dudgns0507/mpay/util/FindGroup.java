package io.github.dudgns0507.mpay.util;

import io.github.dudgns0507.mpay.models.Common;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by pyh42 on 2017-08-28.
 */

public interface FindGroup {
    @FormUrlEncoded
    @POST("/find_group")
    Call<Common> find(@Field("query") String query, @Field("group_id") int group_id);
}
