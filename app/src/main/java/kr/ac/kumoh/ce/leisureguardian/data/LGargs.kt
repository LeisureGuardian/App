package kr.ac.kumoh.ce.leisureguardian.data

import android.app.Application
import java.util.*
import java.util.concurrent.Semaphore

data class LGargs(var LoginToken: String, var useremail: String) {
    var logintoken: String? = null
    var username: String? = null
    var clickPosition = -1
    val available = Semaphore(1, true)
}