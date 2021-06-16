package kr.ac.kumoh.ce.leisureguardian.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.ac.kumoh.ce.leisureguardian.R

class ListFragment : Fragment() {

    private lateinit var listViewModel: ListViewModel
    private val mAdapter = RecyclerAdapterDevices()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        listViewModel = ViewModelProvider(activity as AppCompatActivity).get(ListViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_list, container, false)
        val recyclerView = root.findViewById<RecyclerView>(R.id.rvDevices)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            itemAnimator = DefaultItemAnimator()
            adapter = mAdapter
        }
        return root
    }

    inner class RecyclerAdapterDevices : RecyclerView.Adapter<RecyclerAdapterDevices.ViewHolder>() {
        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val deviceImage: ImageView = itemView.findViewById(R.id.deviceImage)
            val deviceName: TextView = itemView.findViewById(R.id.deviceName)
            val critical: TextView = itemView.findViewById(R.id.critical)
            val batteryLevel: TextView = itemView.findViewById(R.id.batteryLevel)
            val temp: TextView = itemView.findViewById(R.id.temp)
            val accelMax: TextView = itemView.findViewById(R.id.accelMax)
            val heartRate: TextView = itemView.findViewById(R.id.heartRate)
        }
        override fun getItemCount(): Int = listViewModel.getSize()

        override fun onBindViewHolder(holder: RecyclerAdapterDevices.ViewHolder, position: Int) {
            if(listViewModel.getStatus(position).critical == "0") {
                holder.deviceImage.setImageResource(R.drawable.device_green)
            }
            else {
                holder.deviceImage.setImageResource(R.drawable.device_red)
            }
            holder.deviceName.text = listViewModel.getStatus(position).deviceName
            holder.critical.text = listViewModel.getStatus(position).critical
            holder.batteryLevel.text = listViewModel.getStatus(position).batteryLevel + "%"
            holder.temp.text = "체온: " + listViewModel.getStatus(position).temp + "℃"
            holder.accelMax.text = "가속도: " + listViewModel.getStatus(position).accelMax
            holder.heartRate.text = "심박수: " + listViewModel.getStatus(position).heartRate
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
                RecyclerAdapterDevices.ViewHolder {
            val view = layoutInflater.inflate(R.layout.list_devices, parent, false)
            return ViewHolder(view)
        }
    }
}