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

        val loginButton = findViewById<Button>(R.id.loginbutton)
        val password = findViewById<EditText>(R.id.password)
        val userEmail = findViewById<EditText>(R.id.useremail)
        val signupButton = findViewById<Button>(R.id.signupbutton)

        loginButton.setOnClickListener{
            val loginData = LoginData(userEmail.text.toString(), password.text.toString())
            Log.d("test-username", loginData.email)
            Log.d("test-password", loginData.password)

            val retrofit: Retrofit = Retrofit.Builder().baseUrl("http://mmyu.synology.me:8000").
            addConverterFactory(GsonConverterFactory.create()).build()

            val service = retrofit.create(RetrofitAPI::class.java)      // RetrofitAPI 사용
            Log.d("test-Retrofit","시작")
            val request: Call<ResponseData> = service.loginPost(loginData)  // 로그인 확인
            request.enqueue(object: Callback<ResponseData> {
                override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                    if(!response.isSuccessful) {        // 로그인 실패시
                        Log.d("test-Login Fail", response.toString())
                        Toast.makeText(this@LoginActivity, "입력된 정보를 확인하세요", Toast.LENGTH_SHORT).show()
                        loginButton.isClickable = true
                    }
                    else {
                        Log.d("test-Login response", response.toString())
                        val responseData = response.body()  // 로그인 토큰 받음
                        Log.d("test-Login token", responseData.toString())

                        if(responseData?.access_token != null) {
                            val intent = Intent(this@LoginActivity, TabActivity::class.java)
                            Singleton.getInstance().loginToken = responseData.access_token
                            startActivity(intent)
                        }
                        else {
                            Toast.makeText(this@LoginActivity,"로그인 실패", Toast.LENGTH_SHORT).show()
                            loginButton.isClickable = true
                        }
                    }
                }
                override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                    Log.d("test-Login error", t.toString())
                    Toast.makeText(this@LoginActivity, "REST 요청 실패", Toast.LENGTH_SHORT).show()
                    loginButton.isClickable = true
                }
            })
        }
        signupButton.setOnClickListener{
            signupButton.isClickable = false
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
            signupButton.isClickable = true
        }
    }
}