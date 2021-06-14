package kr.ac.kumoh.ce.leisureguardian.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import kr.ac.kumoh.ce.leisureguardian.R
import kr.ac.kumoh.ce.leisureguardian.Singleton
import kr.ac.kumoh.ce.leisureguardian.data.Device
import kr.ac.kumoh.ce.leisureguardian.data.DeviceData
import kr.ac.kumoh.ce.leisureguardian.data.RetrofitAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)

        homeViewModel.text.observe(viewLifecycleOwner, {
            textView.text = it
        })

        val retrofit: Retrofit = Retrofit.Builder().baseUrl("http://mmyu.synology.me:8000")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val service = retrofit.create(RetrofitAPI::class.java)  // RetrofitAPI 사용

        var token = Singleton.getInstance(requireContext()).loginToken
        Log.d("test-Home token", token.toString())
        val request: Call<DeviceData<ArrayList<Device>>> = service.statusGet("Bearer "+"$token")
        Log.d("test-Home request", request.toString())
        request.enqueue(object: Callback<DeviceData<ArrayList<Device>>> {

            override fun onResponse(call: Call<DeviceData<ArrayList<Device>>>, response: Response<DeviceData<ArrayList<Device>>>) {
                Log.d("test-Home response", response.toString())
                Log.d("test-Home response body", response.body().toString())
            }
            override fun onFailure(call: Call<DeviceData<ArrayList<Device>>>, t: Throwable) {
                Log.d("test-Home error", t.toString())
            }
        })
        return root
    }
}