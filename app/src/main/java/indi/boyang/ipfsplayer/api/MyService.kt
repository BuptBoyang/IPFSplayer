package indi.boyang.ipfsplayer.api

import androidx.lifecycle.LiveData
import indi.boyang.ipfsplayer.models.AccountInfo
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
        @Path("id") id: Long,
        @Query("operator") operator: String
    ): LiveData<Video>

    @GET("/users/account/{username}")
    fun getAccount(
        @Path("username") username: String
    ): LiveData<AccountInfo>

    @Multipart
    @POST("/videos")
    fun uploadVideo(
        @Part("title") title: String,
        @Part pic: MultipartBody.Part,
        @Part video: MultipartBody.Part,
        @Part ("uploader")uploader: String
    ): Call<ResponseBody>

    @FormUrlEncoded
    @POST("/users/")
    fun login(
        @Field("username") username:String,
        @Field("password_md5") password_md5:String
    ): Call<ResponseBody>

    @FormUrlEncoded
    @POST("/videos/{id}/motivation")
    fun like(
        @Path("id") id: Long,
        @Field("operator") operator: String
    ): Call<ResponseBody>

    companion object Factory {
        fun create(): MyService {
            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .baseUrl("http://10.0.2.2:8081") //http://10.0.2.2:8081, https://api.nroo.xyz
                .build()
                .create(MyService::class.java)
        }

        fun create2(): MyService {
            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://10.0.2.2:8081") //http://10.0.2.2:8081, https://api.noroo.xyz
                .build()
                .create(MyService::class.java)
        }
    }
}

