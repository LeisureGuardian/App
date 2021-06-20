package kr.ac.kumoh.s20160250.lg

import com.google.android.gms.maps.model.Marker
import kr.ac.kumoh.s20160250.lg.data.StatusData


class Singleton private constructor(){
    companion object{
        private var INSTANCE: Singleton?=null
        fun getInstance()=
            INSTANCE ?: synchronized(this){
                INSTANCE ?: Singleton().also {
                    INSTANCE = it
                }
            }

    }
    lateinit var login_token : String

    var deviceInfo = ArrayList<StatusData>() // 위도 경도값 배열 선언

    var markerList =ArrayList<Marker>()

}