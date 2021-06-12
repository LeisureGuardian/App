package kr.ac.kumoh.s20160250.lg

import android.content.Context

class MySingleton private constructor(context: Context){
    companion object{
        private var INSTANCE: MySingleton?=null
        fun getInstance(context: Context)=
            INSTANCE ?: synchronized(this){
                INSTANCE ?: MySingleton(context).also {
                    INSTANCE =it
                }
            }
    }
    lateinit var login_token : String
}