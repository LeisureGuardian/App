package kr.ac.kumoh.s20160250.lg.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kr.ac.kumoh.s20160250.lg.MySingleton
import kr.ac.kumoh.s20160250.lg.R
import kr.ac.kumoh.s20160250.lg.data.DeviceInfo
import kr.ac.kumoh.s20160250.lg.data.ResponseData
import kr.ac.kumoh.s20160250.lg.data.RetrofitAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var add_device : Button
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        val textView: TextView = root.findViewById(R.id.text2)
        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        add_device.setOnClickListener {
            Log.d("button","버튼눌림")
//            Toast.makeText(requireContext(), "추가됨", Toast.LENGTH_SHORT).show()
//            val retrofit: Retrofit = Retrofit.Builder().baseUrl("http://mmyu.synology.me:8000").
//            addConverterFactory(GsonConverterFactory.create()).build()
//            val service=retrofit.create(RetrofitAPI::class.java) //restrofit api 사용
//
//            var deviceinfo=DeviceInfo(deviceSerial.text.toString(),deviceName.text.toString())
//            var token = MySingleton.getInstance(requireContext()).login_token
//            val request: Call<ResponseData> = service.add_device("Bearer "+"${token}",deviceinfo)//로그인 확인
//            request.enqueue(object : Callback<ResponseData> {
//                override fun onResponse(
//                    call: Call<ResponseData>,
//                    response: Response<ResponseData>
//                ) {
//                    Log.d("response",response.toString())
//                    Log.d("response",response.body().toString())
//                    Toast.makeText(requireContext(), "장치가성공적으로 등록되었습니다", Toast.LENGTH_SHORT).show()
//                }
//
//                override fun onFailure(call: Call<ResponseData>, t: Throwable) {
//                    Toast.makeText(requireContext(), "장치등록실패", Toast.LENGTH_SHORT).show()
//                }
//            })
        }

        return root
    }
}