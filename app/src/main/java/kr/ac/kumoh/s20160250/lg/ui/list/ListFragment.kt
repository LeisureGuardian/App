package kr.ac.kumoh.s20160250.lg.ui.list


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.ac.kumoh.s20160250.lg.R

class ListFragment : Fragment() {

    private lateinit var listViewModel: ListViewModel
    private val mAdapter = device_adapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        listViewModel = ViewModelProvider(activity as AppCompatActivity).get(ListViewModel::class.java)
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
        inner class ViewHolder(itemView:View) :RecyclerView.ViewHolder(itemView){
            val devicename: TextView = itemView.findViewById<TextView>(R.id.deviceName)
            val temp: TextView = itemView.findViewById<TextView>(R.id.temp)
            val image: ImageView = itemView.findViewById<ImageView>(R.id.deviceImage)
            val accelerate :TextView =itemView.findViewById<TextView>(R.id.accelMax)
            val Heartrate :TextView =itemView.findViewById<TextView>(R.id.heartRate)
            val battery :TextView =itemView.findViewById<TextView>(R.id.batteryLevel)

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
            Log.d("test/position",position.toString())
            holder.devicename.text = listViewModel.getStatus(position).deviceName
            holder.temp.text = listViewModel.getStatus(position).temp+"℃"
            if(listViewModel.getStatus(position).critical=="0"){
                holder.image.setImageResource(R.drawable.device_green)
            }
            else{
                holder.image.setImageResource(R.drawable.device_red)
                holder.devicename.setTextColor(ContextCompat.getColor(context!!, R.color.colorRed))
            }
            holder.accelerate.text = "가속도: "+ listViewModel.getStatus(position).accelMax
            holder.Heartrate.text = "심박: "+ listViewModel.getStatus(position).heartRate
            holder.battery.text ="잔여배터리"+ listViewModel.getStatus(position).batteryLevel + "%"


        }



    }
}