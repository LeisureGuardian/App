package kr.ac.kumoh.ce.leisureguardian.ui.list

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

class ListViewModel : ViewModel() {
    val list = MutableLiveData<ArrayList<StatusData>>()
    var statusData = ArrayList<StatusData>()
    private val token: String?

    init {
        list.value= statusData
        token = Singleton.getInstance().loginToken
        updateStatus()
    }

    private fun updateStatus() {
        val retrofit: Retrofit = Retrofit.Builder().baseUrl("http://mmyu.synology.me:8000")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val service = retrofit.create(RetrofitAPI::class.java)  // RetrofitAPI 사용

        val request: Call<DeviceData<ArrayList<StatusData>>> = service.statusListGet("Bearer $token")
        request.enqueue(object :Callback<DeviceData<ArrayList<StatusData>>> {
            override fun onResponse(call: Call<DeviceData<ArrayList<StatusData>>>, response: Response<DeviceData<ArrayList<StatusData>>>) {
                Log.d("test-List response",response.toString())
                Log.d("test-List body",response.body().toString())
                statusData.clear()
                statusData = response.body()?.data?: ArrayList<StatusData>()
                list.value = statusData
                Log.d("test-List", list.value.toString())
            }
            override fun onFailure(call: Call<DeviceData<ArrayList<StatusData>>>, t: Throwable) {
                Log.d("error", t.toString())
            }
        })
    }

    fun getStatus(i:Int)= statusData[i]

    fun getSize() = statusData.size
}