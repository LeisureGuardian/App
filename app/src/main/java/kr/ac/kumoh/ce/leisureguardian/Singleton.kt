package kr.ac.kumoh.ce.leisureguardian

import com.google.android.gms.maps.model.Marker
import kr.ac.kumoh.ce.leisureguardian.data.StatusData

class Singleton private constructor() {
    companion object {
        private var INSTANCE: Singleton ?= null
        fun getInstance() =
            INSTANCE?: synchronized(this) {
                INSTANCE?: Singleton().also {
                    INSTANCE = it
                }
            }
    }
    var loginToken: String? = null
    var deviceInfo = ArrayList<StatusData>()
    var markerList = ArrayList<Marker>()
}