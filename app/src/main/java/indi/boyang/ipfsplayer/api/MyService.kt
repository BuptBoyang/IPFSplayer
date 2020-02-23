package indi.boyang.ipfsplayer.api

import androidx.lifecycle.LiveData
import indi.boyang.ipfsplayer.models.Video
import indi.boyang.ipfsplayer.util.LiveDataCallAdapterFactory
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit


interface MyService {
    @GET("/videos")
    fun getPlaylist(): LiveData<List<Video>>

    @GET("/videos/{id}")
    fun getVideo(
        @Path("id") id: Long
    ): LiveData<Video>

    @Headers("Content-Type:multipart/form-data")
    @Multipart
    @POST("/videos")
    fun uploadVideo(
        @Part("title") title: String,
        @Part pic: MultipartBody.Part,
        @Part video: MultipartBody.Part
    ): Call<ResponseBody>

    @FormUrlEncoded
    @POST("/test")
    fun postTest(
        @Field("num") n:Int
    ):Call<ResponseBody>


    companion object Factory {
        fun create(): MyService {
            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .baseUrl("http://10.0.2.2:8081") //https://api.noroo.xyz, http://35.203.163.14:8081
                .build()
                .create(MyService::class.java)
        }

        fun create2(): MyService {
            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://10.0.2.2:8081") //https://api.noroo.xyz, http://35.203.163.14:8081
                .build()
                .create(MyService::class.java)
        }
    }
}

