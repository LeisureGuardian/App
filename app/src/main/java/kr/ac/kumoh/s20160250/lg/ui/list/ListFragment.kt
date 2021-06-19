package kr.ac.kumoh.s20160250.lg.ui.list


import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.ac.kumoh.s20160250.lg.LoginActivity
import kr.ac.kumoh.s20160250.lg.R
import kr.ac.kumoh.s20160250.lg.data.StatusData

class ListFragment : Fragment() {

    private lateinit var listViewModel: ListViewModel
    private val mAdapter = device_adapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        listViewModel = ViewModelProvider(activity as AppCompatActivity).get(ListViewModel::class.java)
        listViewModel.list.observe(viewLifecycleOwner, Observer<ArrayList<StatusData>> {
            Handler(Looper.getMainLooper()).postDelayed({
            listViewModel.update_status()
            },5000L)
            mAdapter.notifyDataSetChanged()
        })
        val root = inflater.inflate(R.layout.fragment_list, container, false)
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
        inner class ViewHolder(itemView:View): RecyclerView.ViewHolder(itemView){
            val devicename: TextView = itemView.findViewById<TextView>(R.id.deviceName)
            val temp: TextView = itemView.findViewById<TextView>(R.id.temp)
            val image: ImageView = itemView.findViewById<ImageView>(R.id.deviceImage)
            val accelerate :TextView = itemView.findViewById<TextView>(R.id.accelMax)
            val Heartrate :TextView = itemView.findViewById<TextView>(R.id.heartRate)
            val battery :TextView = itemView.findViewById<TextView>(R.id.batteryLevel)
            val cardview : CardView = itemView.findViewById<CardView>(R.id.cardview)

        }
        override fun getItemCount(): Int {
            return listViewModel.getSize()
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): device_adapter.ViewHolder {
            val view = layoutInflater.inflate(
                R.layout.fragment_item,
                parent,
                false)
            return ViewHolder(view)
        }
        override fun onBindViewHolder(holder: device_adapter.ViewHolder, position: Int) {
            holder.devicename.text = listViewModel.getStatus(position).deviceName
            holder.temp.text = listViewModel.getStatus(position).temp+"℃"
            if(listViewModel.getStatus(position).critical=="0" && listViewModel.getStatus(position).button=="0"){
                holder.image.setImageResource(R.drawable.device_green)
                holder.cardview.setCardBackgroundColor(Color.parseColor("#ffffff"))
            }
            else{
                holder.image.setImageResource(R.drawable.device_red)
                holder.cardview.setCardBackgroundColor(Color.parseColor("#ffe4e1"))
            }
            holder.accelerate.text = "가속도: "+ listViewModel.getStatus(position).accelMax
            holder.Heartrate.text = "심박: "+ listViewModel.getStatus(position).heartRate
            holder.battery.text ="배터리"+ listViewModel.getStatus(position).batteryLevel + "%"
        }
    }

}