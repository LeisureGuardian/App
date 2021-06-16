package kr.ac.kumoh.ce.leisureguardian.data

data class StatusData(
    var deviceName : String,
    var longitude: String,
    var latitude: String,
    var temp: String,
    var accelMax: String,
    var heartRate: String,
    var batteryLevel: String,
    var critical: String,
    var button: String
)