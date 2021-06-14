package kr.ac.kumoh.ce.leisureguardian

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