package kr.ac.kumoh.s20160250.lg.ui.home

import android.app.Application
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.android.synthetic.main.activity_login.*
import kr.ac.kumoh.s20160250.lg.MySingleton
import kr.ac.kumoh.s20160250.lg.data.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeViewModel (application: Application) : AndroidViewModel(application) {

    val list = MutableLiveData<ArrayList<StatusData>>()
    var statusdata = ArrayList<StatusData>()
    var token : String?

    init {
        list.value= statusdata
        token = MySingleton.getInstance(application).login_token
        update_status()
    }
    fun update_status() {
        val retrofit: Retrofit = Retrofit.Builder().baseUrl("http://mmyu.synology.me:8000").
        addConverterFactory(GsonConverterFactory.create()).build()
        val service = retrofit.create(RetrofitAPI::class.java) //restrofit api 사용

        val request: Call<DeviceData<ArrayList<StatusData>>> = service.statusListGet("Bearer "+"${token}")
        request.enqueue(object :Callback<DeviceData<ArrayList<StatusData>>>{
            override fun onResponse(call: Call<DeviceData<ArrayList<StatusData>>>, response: Response<DeviceData<ArrayList<StatusData>>>) {
                Log.d("response",response.toString())
                Log.d("response.body",response.body().toString())
                statusdata.clear()
                statusdata= response.body()?.data!!
                list.value=statusdata
                Log.d("list",list.value.toString())
            }
            override fun onFailure(call: Call<DeviceData<ArrayList<StatusData>>>, t: Throwable) {
                Log.d("error",t.toString())
            }
        })
    }

    override fun onCleared() {
        super.onCleared()
    }

    fun getStatus(i:Int)= statusdata[i]
    fun getSize() = statusdata.size



}