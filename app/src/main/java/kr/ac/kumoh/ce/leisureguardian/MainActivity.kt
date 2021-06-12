package kr.ac.kumoh.ce.leisureguardian

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT     // 세로화면 고정
        Handler(Looper.getMainLooper()).postDelayed({ startActivity(
            Intent(this,
                LoginActivity::class.java)
        )},2000L)   // 2초 지연후 다음 화면으로 이동
    }
}