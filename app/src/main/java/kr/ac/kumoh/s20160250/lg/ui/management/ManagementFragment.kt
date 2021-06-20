package kr.ac.kumoh.s20160250.lg.ui.management

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_management.*
import kotlinx.android.synthetic.main.fragment_management.view.*
import kr.ac.kumoh.s20160250.lg.Singleton
import kr.ac.kumoh.s20160250.lg.R
import kr.ac.kumoh.s20160250.lg.data.DeviceInfo
import kr.ac.kumoh.s20160250.lg.data.ResponseData
import kr.ac.kumoh.s20160250.lg.data.ResponseDevice
import kr.ac.kumoh.s20160250.lg.data.RetrofitAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ManagementFragment : Fragment() {


    private lateinit var add_device : Button
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

//        val root = inflater.inflate(R.layout.fragment_management, container, false)
        val view: View = inflater!!.inflate(R.layout.fragment_management, container, false)
//        val textView: TextView = root.findViewById(R.id.text2)
//        val add : Button = root.findViewById(R.id.add_device)
//        val delete : Button = root.findViewById(R.id.delete_device)
        view.add_device.setOnClickListener{
            view ->  Log.d("button","추가버튼눌림")

            val retrofit: Retrofit = Retrofit.Builder().baseUrl("http://mmyu.synology.me:8000").
            addConverterFactory(GsonConverterFactory.create()).build()
            val service=retrofit.create(RetrofitAPI::class.java) //retrofit api 사용

            var deviceinfo=DeviceInfo(deviceSerial.text.toString(),deviceName.text.toString())
            Log.d("deviceinfo",deviceinfo.toString())
            var token = Singleton.getInstance().login_token
            val request: Call<ResponseData> = service.add_device("Bearer "+"${token}",deviceinfo)
            request.enqueue(object : Callback<ResponseData> {
                override fun onResponse(
                    call: Call<ResponseData>,
                    response: Response<ResponseData>
                ) {
                    Log.d("response",response.toString())
                    Log.d("response",response.body().toString())

                    if(response.body().toString()!=null){
                        Toast.makeText(requireContext(), "장치가 성공적으로 등록되었습니다", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        Toast.makeText(requireContext(), "장치가 등록되지않았습니다", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                    Toast.makeText(requireContext(), "장치등록실패", Toast.LENGTH_SHORT).show()
                }
            })
        }
        view.delete_device.setOnClickListener{
                view ->  Log.d("button","삭제버튼눌림")
            val retrofit: Retrofit = Retrofit.Builder().baseUrl("http://mmyu.synology.me:8000").
            addConverterFactory(GsonConverterFactory.create()).build()
            val service=retrofit.create(RetrofitAPI::class.java) //restrofit api 사용
            var token = Singleton.getInstance().login_token
            val request: Call<ResponseDevice> = service.delete_device(deviceSerial.text.toString() ,"Bearer "+"${token}")//로그인 확인
            request.enqueue(object : Callback<ResponseDevice> {
                override fun onResponse(
                    call: Call<ResponseDevice>,
                    response: Response<ResponseDevice>
                ) {
                    Log.d("response",response.toString())
                    Log.d("response",response.body().toString())
                    Toast.makeText(requireContext(), "장치가성공적으로 삭제되었습니다", Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<ResponseDevice>, t: Throwable) {
                    Toast.makeText(requireContext(), "장치삭제실패", Toast.LENGTH_SHORT).show()
                }
            })
        }

        return view
    }
}