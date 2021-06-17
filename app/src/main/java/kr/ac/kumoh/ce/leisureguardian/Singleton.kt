package kr.ac.kumoh.ce.leisureguardian

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
}