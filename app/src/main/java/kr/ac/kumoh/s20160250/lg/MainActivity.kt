package kr.ac.kumoh.s20160250.lg

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT //화면 세로모드 고정
        Handler(Looper.getMainLooper()).postDelayed({ startActivity(
            Intent(this,
            LoginActivity::class.java)
        )},1500L) //1.5초 지연후 다음화면으로 이동
    }
}