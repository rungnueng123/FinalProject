package com.android.orc.cloudvision;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by j.poobest on 14/12/2017 AD.
 */

public interface CVService {
    @POST(CVUrl.IMAGE_ANNOTATE)
    Call<CVResponse> runImageDetection(@Query("key") String apiKey, @Body CVRequest request);
}

