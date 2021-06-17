package kr.ac.kumoh.s20160250.lg

import android.content.Context
import kr.ac.kumoh.s20160250.lg.data.StatusData

class MySingleton private constructor(){
    companion object{
        private var INSTANCE: MySingleton?=null
        fun getInstance()=
            INSTANCE ?: synchronized(this){
                INSTANCE ?: MySingleton().also {
                    INSTANCE =it
                }
            }

    }
    lateinit var login_token : String


}