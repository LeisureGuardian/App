package kr.ac.kumoh.ce.leisureguardian.data

import retrofit2.Call
import retrofit2.http.*
import kotlin.collections.ArrayList

interface RetrofitAPI {
    @POST("/user/signup")
    fun signupPost(@Body signUpData: SignUpData): Call<ResponseData>

    @POST("/user/login")
    fun loginPost(@Body loginData: LoginData?): Call<ResponseData>

    @POST("/device")
    fun addDevice(
        @Header("Authorization") token: String?,
        @Body deviceInfo: DeviceInfo): Call<ResponseData>

    @DELETE("/device/{id}")
    fun deleteDevice(
        @Path("id") deviceSerial: String?,
        @Header("Authorization") token: String?
    ) :Call<ResponseDevice>

    @GET("/deviceData")
    fun statusListGet(
        @Header("Authorization") token: String?): Call<DeviceData<ArrayList<StatusData>>>
}
