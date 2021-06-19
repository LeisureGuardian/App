package kr.ac.kumoh.s20160250.lg

import com.google.android.gms.maps.model.Marker
import kr.ac.kumoh.s20160250.lg.data.StatusData


class MySingleton private constructor(){
    companion object{
        private var INSTANCE: MySingleton?=null
        fun getInstance()=
            INSTANCE ?: synchronized(this){
                INSTANCE ?: MySingleton().also {
                    INSTANCE = it
                }
            }

    }
    lateinit var login_token : String

    var deviceinfo = ArrayList<StatusData>() // 위도 경도값 배열 선언

    var markerList =ArrayList<Marker>()

}