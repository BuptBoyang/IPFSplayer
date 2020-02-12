package indi.boyang.ipfsplayer.api

import androidx.lifecycle.LiveData
import indi.boyang.ipfsplayer.models.Video
import indi.boyang.ipfsplayer.util.LiveDataCallAdapterFactory
import okhttp3.MultipartBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface MyService {
    @GET("/videos")
    fun getPlaylist(): LiveData<List<Video>>

    @GET("/videos/{id}")
    fun getVideo(
        @Path("id") id: Long
    ): LiveData<Video>

    @FormUrlEncoded
    @POST("/videos")
    fun uploadVideo(
        @Field("title") title: String,
        @Field("pic") picFile: MultipartBody.Part,
        @Field("video") videoFile: MultipartBody.Part
    ):LiveData<Video>

    companion object Factory {
        fun create(): MyService {
            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .baseUrl("http://35.203.163.14:8080") //https://api.noroo.xyz, http://10.0.2.2:8080
                .build()
                .create(MyService::class.java)
        }
    }
}