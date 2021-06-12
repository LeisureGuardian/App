package kr.ac.kumoh.ce.leisureguardian.data

data class StatusData(
    private var _id: String,
    var updatedAt: String,
    var createdAt: String,
    var devicename: String,
    var emergency: String,
    var degreeX: String,
    var degreeY: String,
    var degreeZ: String,
    var longitude: String,
    var latitude: String,
    var __v: String
)