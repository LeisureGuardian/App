package kr.ac.kumoh.s20160250.lg.data

import retrofit2.Call
import retrofit2.http.*
import kotlin.collections.ArrayList

interface RetrofitAPI {
    @POST("/user/login")
    fun loginPost(@Body logindata: LoginData): Call<ResponseData>

//    @GET("/devicedata")
//    suspend fun deviceGet(
//        @Path("owner") owner: String?,
//        @Header("x-access-token") token: String?
//    ): Call<ResponseData<ArrayList<DeviceData>>>
//
    @GET("/device")
    fun statusGet(
        @Header("Authorization") token: String?): Call<DeviceData<ArrayList<Device>>>

    @POST("/user/signup")
    fun signupPost(@Body signUpData: SignUpData): Call<ResponseData>
}