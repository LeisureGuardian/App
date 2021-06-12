package kr.ac.kumoh.s20160250.lg.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kr.ac.kumoh.s20160250.lg.MySingleton

class HomeViewModel (application: Application) : AndroidViewModel(application) {

    private val _text = MutableLiveData<String>().apply {
        value = MySingleton.getInstance(application).login_token
    }
    val text: LiveData<String> = _text
}