package com.example.gifstoreinlocal;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ServerAPI {
    @GET
    Call<ResponseBody> downlload(@Url String fileUrl);

}
