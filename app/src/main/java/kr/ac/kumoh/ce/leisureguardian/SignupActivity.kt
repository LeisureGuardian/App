package kr.ac.kumoh.ce.leisureguardian

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kr.ac.kumoh.ce.leisureguardian.data.ResponseData
import kr.ac.kumoh.ce.leisureguardian.data.RetrofitAPI
import kr.ac.kumoh.ce.leisureguardian.data.SignUpData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val signupbutton2 = findViewById<Button>(R.id.signupbutton2)
        val organization = findViewById<EditText>(R.id.organization)
        val newEmail = findViewById<EditText>(R.id.newEmail)
        val newName = findViewById<EditText>(R.id.newName)
        val newPassword = findViewById<EditText>(R.id.newPassword)

        signupbutton2.setOnClickListener {
            var signupdata = SignUpData(organization.text.toString(), newName.text.toString(), newEmail.text.toString(), newPassword.text.toString())
            Log.d("check",signupdata.toString())

            val retrofit = Retrofit.Builder()
                    .baseUrl("http://mmyu.synology.me:8000/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            val service: RetrofitAPI = retrofit.create(RetrofitAPI::class.java)

            val request: Call<ResponseData> = service.signupPost(signupdata)
            request.enqueue(object : Callback<ResponseData>{
                override fun onResponse(
                        call: Call<ResponseData>,
                        response: Response<ResponseData>
                ) {
                    if(response.isSuccessful) {
                        Log.d("response data", response.toString())
                        val responseData = response.body()
                        Log.d("response data", responseData.toString())

                        if(responseData != null) {
                            Toast.makeText(this@SignupActivity, "계정이 성공적으로 등록됨", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                        else {
                            Toast.makeText(this@SignupActivity, "계정 등록 실패", Toast.LENGTH_SHORT).show()
                        }
                    }
                    else
                    {
                        Log.d("fail", response.toString())
                        Log.d("response", response.body().toString())
                        Toast.makeText(this@SignupActivity,"입력된 정보를 확인하시오", Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(
                        call: Call<ResponseData>,
                        t: Throwable
                ) {
                    Log.d("sign error", t.toString())
                    Toast.makeText(this@SignupActivity,"rest 요청 실패", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}