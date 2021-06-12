package kr.ac.kumoh.s20160250.lg.data

import android.widget.Button

data class StatusData (
    var deviceSerial : String,
    var longitude: String,
    var latitude: String,
    var temp: String,
    var accelMAx: String,
    var heartRate: String,
    var critical: String,
    var button: String
)