package kr.ac.kumoh.ce.leisureguardian.ui.map

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kr.ac.kumoh.ce.leisureguardian.Singleton
import kr.ac.kumoh.ce.leisureguardian.data.DeviceData
import kr.ac.kumoh.ce.leisureguardian.data.RetrofitAPI
import kr.ac.kumoh.ce.leisureguardian.data.StatusData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MapViewModel : ViewModel() {
    val list = MutableLiveData<ArrayList<StatusData>>()
    var statusData = ArrayList<StatusData>()
    private val token: String?

    init {
        list.value = statusData
        token = Singleton.getInstance().loginToken
        updateStatus()
    }

    fun updateStatus() {
        val retrofit: Retrofit = Retrofit.Builder().baseUrl("http://mmyu.synology.me:8000")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val service = retrofit.create(RetrofitAPI::class.java)  // RetrofitAPI 사용

        val request: Call<DeviceData<ArrayList<StatusData>>> = service.statusListGet("Bearer $token")
        request.enqueue(object :Callback<DeviceData<ArrayList<StatusData>>> {
            override fun onResponse(call: Call<DeviceData<ArrayList<StatusData>>>, response: Response<DeviceData<ArrayList<StatusData>>>) {
                Log.d("test-Map response", response.toString())
                Log.d("test-Map body", response.body().toString())
                statusData.clear()
                statusData = response.body()?.data?: ArrayList()
                list.value = statusData
                Log.d("test-Map", list.value.toString())
                Singleton.getInstance().deviceInfo.clear()
                Singleton.getInstance().deviceInfo = statusData
            }
            override fun onFailure(call: Call<DeviceData<ArrayList<StatusData>>>, t: Throwable) {
                Log.d("test-Map error", t.toString())
            }
        })
    }
}