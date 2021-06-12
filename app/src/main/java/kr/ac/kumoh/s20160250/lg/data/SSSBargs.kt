package kr.ac.kumoh.s20160250.lg.data

import android.app.Application
import android.telephony.mbms.StreamingServiceInfo

data class SSSBargs(var LoginToken: String, var useremail: String) {

    //    var LoginToken: String
//    var username: String
    val clickPosition = -1
    val deviceDataArrayList: ArrayList<DeviceData>? = null
    val statusDataArrayList: ArrayList<StatusData>? = null
    val statusDataTempArrayList =
        ArrayList<StatusData>()
}