package kr.ac.kumoh.s20160250.lg.ui.home


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kr.ac.kumoh.s20160250.lg.MySingleton
import kr.ac.kumoh.s20160250.lg.R
import kr.ac.kumoh.s20160250.lg.data.Device
import kr.ac.kumoh.s20160250.lg.data.DeviceData
import kr.ac.kumoh.s20160250.lg.data.ResponseData
import kr.ac.kumoh.s20160250.lg.data.RetrofitAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    var token :String?=null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)

        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        val retrofit: Retrofit = Retrofit.Builder().baseUrl("http://mmyu.synology.me:8000").
        addConverterFactory(GsonConverterFactory.create()).build()
        val service=retrofit.create(RetrofitAPI::class.java) //restrofit api 사용

        var token =MySingleton.getInstance(requireContext()).login_token
        val request: Call<DeviceData<ArrayList<Device>>> = service.statusGet("Bearer "+"${token}")
        request.enqueue(object :Callback<DeviceData<ArrayList<Device>>>{
            override fun onResponse(call: Call<DeviceData<ArrayList<Device>>>, response: Response<DeviceData<ArrayList<Device>>>) {
                Log.d("response",response.toString())
                Log.d("response.body",response.body().toString())
            }

            override fun onFailure(call: Call<DeviceData<ArrayList<Device>>>, t: Throwable) {
                Log.d("error",t.toString())
            }
        })
        return root
    }
}