package io.github.dudgns0507.mpay.util;

import io.github.dudgns0507.mpay.models.Common;
import io.github.dudgns0507.mpay.models.CommonRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by pyh42 on 2017-08-28.
 */

public interface NewGroup {
    @Headers("Content-type: application/json")
    @POST("/new_group")
    Call<Common> newGroup(@Body CommonRequest req);
}
