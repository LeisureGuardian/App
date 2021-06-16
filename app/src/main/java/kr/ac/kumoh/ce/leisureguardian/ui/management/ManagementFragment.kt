package kr.ac.kumoh.ce.leisureguardian.ui.management

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import kr.ac.kumoh.ce.leisureguardian.R
import kr.ac.kumoh.ce.leisureguardian.Singleton
import kr.ac.kumoh.ce.leisureguardian.data.DeviceInfo
import kr.ac.kumoh.ce.leisureguardian.data.ResponseData
import kr.ac.kumoh.ce.leisureguardian.data.ResponseDevice
import kr.ac.kumoh.ce.leisureguardian.data.RetrofitAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ManagementFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_management, container, false)
        val addDevice: Button = root.findViewById(R.id.add_device)
        val deleteDevice: Button = root.findViewById(R.id.delete_device)
        val deviceSerial: EditText = root.findViewById(R.id.deviceSerial)
        val deviceName: EditText = root.findViewById(R.id.deviceName)

        addDevice.setOnClickListener {
            Log.d("test-Device add", "등록 버튼 클릭")
            val retrofit: Retrofit = Retrofit.Builder().baseUrl("http://mmyu.synology.me:8000")
                .addConverterFactory(GsonConverterFactory.create()).build()
            val service = retrofit.create(RetrofitAPI::class.java)  // RetrofitAPI 사용

            val deviceInfo = DeviceInfo(deviceSerial.text.toString(), deviceName.text.toString())
            Log.d("test-Device deviceInfo", deviceInfo.toString())
            val token = Singleton.getInstance().loginToken
            val request: Call<ResponseData> = service.add_device("Bearer $token", deviceInfo)
            request.enqueue(object: Callback<ResponseData> {
                override fun onResponse(
                    call: Call<ResponseData>,
                    response: Response<ResponseData>
                ) {
                    Log.d("test-Device response", response.toString())
                    Log.d("test-Device body", response.body().toString())
                    Toast.makeText(requireContext(), "장치가 성공적으로 등록되었습니다", Toast.LENGTH_SHORT).show()
                }
                override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                    Toast.makeText(requireContext(), "장치 등록 실패", Toast.LENGTH_SHORT).show()
                }
            })
        }
        deleteDevice.setOnClickListener {
            Log.d("test-Device delete","삭제 버튼 눌림")
            val retrofit: Retrofit = Retrofit.Builder().baseUrl("http://mmyu.synology.me:8000")
                .addConverterFactory(GsonConverterFactory.create()).build()
            val service = retrofit.create(RetrofitAPI::class.java)  // RetrofitAPI 사용
            val token = Singleton.getInstance().loginToken
            val request: Call<ResponseDevice> = service.delete_device(deviceSerial.text.toString() ,"Bearer $token")    // 로그인 확인
            request.enqueue(object: Callback<ResponseDevice> {
                override fun onResponse(
                    call: Call<ResponseDevice>,
                    response: Response<ResponseDevice>
                ) {
                    Log.d("test-Device response", response.toString())
                    Log.d("test-Device body", response.body().toString())
                    Toast.makeText(requireContext(), "장치가 성공적으로 삭제되었습니다", Toast.LENGTH_SHORT).show()
                }
                override fun onFailure(call: Call<ResponseDevice>, t: Throwable) {
                    Toast.makeText(requireContext(), "장치 삭제 실패", Toast.LENGTH_SHORT).show()
                }
            })
        }
        return root
    }
}