package io.github.dudgns0507.mpay.util;

import io.github.dudgns0507.mpay.models.Common;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by pyh42 on 2017-08-28.
 */

public interface MyGroup {
    @FormUrlEncoded
    @POST("/my_group")
    Call<Common> myGroup(@Field("id") int id);
}
