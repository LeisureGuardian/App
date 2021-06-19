package kr.ac.kumoh.s20160250.lg.ui.maps

import android.util.Log

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kr.ac.kumoh.s20160250.lg.MySingleton
import kr.ac.kumoh.s20160250.lg.data.DeviceData
import kr.ac.kumoh.s20160250.lg.data.RetrofitAPI
import kr.ac.kumoh.s20160250.lg.data.StatusData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MapsViewModel : ViewModel() {

    val list = MutableLiveData<ArrayList<StatusData>>()
    var statusdata = ArrayList<StatusData>()
    var token : String?

    init {
        list.value= statusdata
        token = MySingleton.getInstance().login_token
        update_status()
    }
    fun update_status() {
        val retrofit: Retrofit = Retrofit.Builder().baseUrl("http://mmyu.synology.me:8000").
        addConverterFactory(GsonConverterFactory.create()).build()
        val service = retrofit.create(RetrofitAPI::class.java) //restrofit api 사용

        val request: Call<DeviceData<ArrayList<StatusData>>> = service.statusListGet("Bearer "+"${token}")
        request.enqueue(object : Callback<DeviceData<ArrayList<StatusData>>> {
            override fun onResponse(call: Call<DeviceData<ArrayList<StatusData>>>, response: Response<DeviceData<ArrayList<StatusData>>>) {
                Log.d("test/map/response",response.toString())
                Log.d("test/map/response.body",response.body().toString())
                statusdata.clear()
                statusdata= response.body()?.data!!
                list.value=statusdata
                Log.d("test/map/list",list.value.toString())
                MySingleton.getInstance().deviceinfo.clear()
                MySingleton.getInstance().deviceinfo = statusdata // 싱글톤 객체에 값저장
            }
            override fun onFailure(call: Call<DeviceData<ArrayList<StatusData>>>, t: Throwable) {
                Log.d("test/map/error",t.toString())
            }
        })
    }

    override fun onCleared() {
        super.onCleared()
    }

    fun getStatus(i:Int)= statusdata[i]
    fun getSize() = statusdata.size



}