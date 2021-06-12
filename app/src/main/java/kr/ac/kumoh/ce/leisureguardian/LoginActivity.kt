package kr.ac.kumoh.ce.leisureguardian

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kr.ac.kumoh.ce.leisureguardian.data.LoginData
import kr.ac.kumoh.ce.leisureguardian.data.ResponseData
import kr.ac.kumoh.ce.leisureguardian.data.RetrofitAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginbutton = findViewById<Button>(R.id.loginbutton)
        val password = findViewById<EditText>(R.id.password)
        val useremail = findViewById<EditText>(R.id.useremail)
        val signupbutton = findViewById<Button>(R.id.signupbutton)

        loginbutton.setOnClickListener{
            val loginData = LoginData(useremail.text.toString(), password.text.toString())
            Log.d("test-username", loginData.email)
            Log.d("test-password", loginData.password)

            val retrofit: Retrofit = Retrofit.Builder().baseUrl("http://mmyu.synology.me:8000").
            addConverterFactory(GsonConverterFactory.create()).build()

            val service = retrofit.create(RetrofitAPI::class.java)    // RestrofitAPI 사용
            Log.d("test-Restrofit","시작")
            val request: Call<ResponseData> = service.loginPost(loginData)    // 로그인 확인
            request.enqueue(object: Callback<ResponseData> {
                override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                    if(!response.isSuccessful) { // 로그인 실패시
                        Log.d("test-Login Fail", response.toString())
                        Toast.makeText(this@LoginActivity, response.toString(), Toast.LENGTH_SHORT).show()
                        loginbutton.isClickable = true    // 로그인 버튼 클릭가능
                    }
                    else {
                        Log.d("test-Response", response.toString())
                        val responseData = response.body()  // 로그인 토큰 받음
                        Log.d("test-Response", responseData.toString())

                        if(responseData?.access_token != null) {
                            val intent = Intent(this@LoginActivity, TabActivity::class.java)
                            Singleton.getInstance(this@LoginActivity).login_token = responseData.access_token
                            startActivity(intent)
                        }
                        else {
                            Toast.makeText(this@LoginActivity,"로그인 실패", Toast.LENGTH_SHORT).show()
                            loginbutton.isClickable = true // 로그인 버튼 클릭가능
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                    Log.d("test-call",t.toString())
                    Toast.makeText(this@LoginActivity, "계정과 비밀번호를 입력하세요", Toast.LENGTH_SHORT).show()
                    loginbutton.isClickable = true    // 로그인 버튼 클릭가능
                }
            })
        }
        signupbutton.setOnClickListener{
            signupbutton.isClickable = false
            val intent = Intent(this,
                SignupActivity::class.java)
            startActivity(intent)
            signupbutton.isClickable = true
        }
    }
}