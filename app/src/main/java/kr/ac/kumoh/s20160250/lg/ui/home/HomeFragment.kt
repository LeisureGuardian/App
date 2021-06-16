package kr.ac.kumoh.s20160250.lg.ui.home


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.ac.kumoh.s20160250.lg.MySingleton
import kr.ac.kumoh.s20160250.lg.R
import kr.ac.kumoh.s20160250.lg.data.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private val mAdapter = device_adapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProvider(activity as AppCompatActivity).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val result = root.findViewById<RecyclerView>(R.id.deviceResult)
        result.apply {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            itemAnimator = DefaultItemAnimator()
            adapter = mAdapter
        }
        return root
    }
    inner class device_adapter : RecyclerView.Adapter<device_adapter.ViewHolder>(){
        inner class ViewHolder(itemView:View) :RecyclerView.ViewHolder(itemView){
            val devicename: TextView = itemView.findViewById<TextView>(R.id.devicename)
            val temp: TextView = itemView.findViewById<TextView>(R.id.temp)
            val image: ImageView = itemView.findViewById<ImageView>(R.id.deviceimage)
            val accelerate :TextView =itemView.findViewById<TextView>(R.id.accelerate)
            val Heartrate :TextView =itemView.findViewById<TextView>(R.id.heartrate)
            val battery :TextView =itemView.findViewById<TextView>(R.id.batterylevel)

        }
        override fun getItemCount(): Int {
            return homeViewModel.getSize()
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): device_adapter.ViewHolder {
            val view = layoutInflater.inflate(
                R.layout.fragment_item,
                parent,
                false)
            return ViewHolder(view)
        }
        override fun onBindViewHolder(holder: device_adapter.ViewHolder, position: Int) {
            holder.devicename.text = homeViewModel.getStatus(position).deviceName
            holder.temp.text = homeViewModel.getStatus(position).temp+"℃"
            if(homeViewModel.getStatus(position).critical=="0"){
                holder.image.setImageResource(R.drawable.device_green)
            }
            else{
                holder.image.setImageResource(R.drawable.device_red)
            }
            holder.accelerate.text = "가속도: "+ homeViewModel.getStatus(position).accelMax
            holder.Heartrate.text = "심박: "+ homeViewModel.getStatus(position).heartRate
            holder.battery.text ="잔여데이터"+ homeViewModel.getStatus(position).batteryLevel + "%"


        }



    }
}