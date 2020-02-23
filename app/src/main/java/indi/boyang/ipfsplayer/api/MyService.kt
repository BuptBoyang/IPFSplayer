package indi.boyang.ipfsplayer.api

import androidx.lifecycle.LiveData
import indi.boyang.ipfsplayer.models.Video
import indi.boyang.ipfsplayer.util.LiveDataCallAdapterFactory
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface MyService {
    @GET("/videos")
    fun getPlaylist(): LiveData<ApiResponse<List<Video>>>

    @GET("/videos/{id}")
    fun getVideo(
        @Path("id") id: Long
    ): LiveData<ApiResponse<Video>>

    @Multipart
    @POST("/videos")
    fun uploadVideo(
        @Part("title") title: String,
        @Part picFile: MultipartBody.Part,
        @Part videoFile: MultipartBody.Part
    ):LiveData<ApiResponse<Video>>

    companion object Factory {
        fun create(): MyService {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .client(client)
                .baseUrl("http://35.203.163.14:8081") //https://api.noroo.xyz, http://10.0.2.2:8081
                .build()
                .create(MyService::class.java)
        }
    }
}