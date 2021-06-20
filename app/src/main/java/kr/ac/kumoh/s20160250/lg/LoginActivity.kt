package kr.ac.kumoh.s20160250.lg

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import kr.ac.kumoh.s20160250.lg.data.LoginData
import kr.ac.kumoh.s20160250.lg.data.ResponseData
import kr.ac.kumoh.s20160250.lg.data.RetrofitAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity()  {
    companion object{
            const val token= "token"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loginbutton.setOnClickListener{
            val loginData = LoginData(useremail.text.toString(),password.text.toString())
            Log.d("username",loginData.email)
            Log.d("password",loginData.password)
            val retrofit: Retrofit = Retrofit.Builder().baseUrl("http://mmyu.synology.me:8000").
            addConverterFactory(GsonConverterFactory.create()).build()
            val service=retrofit.create(RetrofitAPI::class.java) //restrofit api 사용
            Log.d("restrofit","시작")
            val request: Call<ResponseData> = service.loginPost(loginData)//로그인 확인
            request.enqueue(object: Callback<ResponseData> {
                override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                    if(!response.isSuccessful)
                    { //로그인 실패시
                        Log.d("login fail",response.toString())
                        Toast.makeText(this@LoginActivity,response.toString(),Toast.LENGTH_SHORT).show()
                        loginbutton.isClickable=true //로그인버튼 클릭가능
                    }
                    else
                    {
                        Log.d("response",response.toString())
                        val responseData = response.body()// 로그인 토큰 받음
                        Log.d("response",responseData.toString())


                        if(responseData?.access_token!=null){
                            val intent= Intent(this@LoginActivity,TabActivity::class.java)
                                Singleton.getInstance().login_token=responseData.access_token
                                startActivity(intent)
                            }
                        else{
                                Toast.makeText(this@LoginActivity,"로그인 실패",Toast.LENGTH_SHORT).show()
                                loginbutton.isClickable=true //로그인버튼 클릭가능
                        }


                    }
                }

                override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                    Log.d("call",t.toString())
                    Toast.makeText(this@LoginActivity,"계정과 비밀번호를 등록하시오",Toast.LENGTH_SHORT).show()
                    loginbutton.isClickable=true //로그인버튼 클릭가능
                }
            })

        }

        signupbutton.setOnClickListener{
            signupbutton.isClickable=false
            val intent = Intent(
                this,
                SignupActivity::class.java
            )
            startActivity(intent)
            signupbutton.isClickable=true
        }

    }

}