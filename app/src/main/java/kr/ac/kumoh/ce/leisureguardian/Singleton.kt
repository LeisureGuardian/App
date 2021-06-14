package kr.ac.kumoh.ce.leisureguardian

import android.content.Context

class Singleton private constructor(context: Context) {
    companion object {
        private var INSTANCE: Singleton ?= null
        fun getInstance(context: Context) =
            INSTANCE?: synchronized(this) {
                INSTANCE?: Singleton(context).also {
                    INSTANCE = it
                }
            }
    }
    lateinit var loginToken: String
}