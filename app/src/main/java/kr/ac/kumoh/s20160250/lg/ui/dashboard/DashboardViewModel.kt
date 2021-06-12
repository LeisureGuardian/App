package kr.ac.kumoh.s20160250.lg.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DashboardViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "장치 등록"
    }
    val text: LiveData<String> = _text
}