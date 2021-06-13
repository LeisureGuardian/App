package kr.ac.kumoh.s20160250.lg.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.android.synthetic.main.activity_login.*
import kr.ac.kumoh.s20160250.lg.MySingleton
import kr.ac.kumoh.s20160250.lg.data.LoginData
import kr.ac.kumoh.s20160250.lg.data.ResponseData
import kr.ac.kumoh.s20160250.lg.data.RetrofitAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeViewModel (application: Application) : AndroidViewModel(application) {

    private val _text = MutableLiveData<String>().apply {
        value = MySingleton.getInstance(application).login_token
    }
    val text: LiveData<String> = _text



}