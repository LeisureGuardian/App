package kr.ac.kumoh.ce.leisureguardian.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import kr.ac.kumoh.ce.leisureguardian.R

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private var addDevice: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        val textView: TextView = root.findViewById(R.id.text2)
        dashboardViewModel.text.observe(viewLifecycleOwner, {
            textView.text = it
        })

        addDevice?.setOnClickListener {
            Log.d("test-Dash button", "버튼 클릭")
//            val retrofit: Retrofit = Retrofit.Builder().baseUrl("http://mmyu.synology.me:8000")
//                .addConverterFactory(GsonConverterFactory.create()).build()
//            val service = retrofit.create(RetrofitAPI::class.java)  // RetrofitAPI 사용
//
//            val deviceInfo = DeviceInfo(deviceSerial.text.toString(), deviceName.text.toString())
//            val token = Singleton.getInstance(requireContext()).loginToken
//            val request: Call<ResponseData> = service.add_device("Bearer "+"$token", deviceInfo)  // 로그인 확인
//            request.enqueue(object : Callback<ResponseData> {
//                override fun onResponse(
//                    call: Call<ResponseData>,
//                    response: Response<ResponseData>
//                ) {
//                    Log.d("test-Dash response", response.toString())
//                    Log.d("test-Dash response body", response.body().toString())
//                    Toast.makeText(requireContext(), "장치가 성공적으로 등록되었습니다", Toast.LENGTH_SHORT).show()
//                }
//
//                override fun onFailure(call: Call<ResponseData>, t: Throwable) {
//                    Toast.makeText(requireContext(), "장치 등록 실패", Toast.LENGTH_SHORT).show()
//                }
//            })
        }
        return root
    }
}