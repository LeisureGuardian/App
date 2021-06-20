package kr.ac.kumoh.s20160250.lg.data

import retrofit2.Call
import retrofit2.http.*
import kotlin.collections.ArrayList

interface RetrofitAPI {
    @POST("/user/login")
    fun loginPost(@Body logindata: LoginData): Call<ResponseData>

    @GET("/devicedata")
    fun deviceGet(
        @Header("Authorization") token: String?
    ): Call<DeviceData<ArrayList<StatusData>>>

    @DELETE("/device/{id}")
    fun delete_device(
        @Path("id") deviceSerial :String?,
        @Header("Authorization") token: String?
    ) :Call<ResponseDevice>

    @POST("/device")
    fun add_device(
    @Header("Authorization") token: String?,
    @Body deviceInfo: DeviceInfo) : Call<ResponseData>

    @GET("/deviceData")
    fun statusListGet(
        @Header("Authorization") token: String?): Call<DeviceData<ArrayList<StatusData>>>


    @POST("/user/signup")
    fun signupPost(@Body signUpData: SignUpData): Call<ResponseData>
}