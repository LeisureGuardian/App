package kr.ac.kumoh.ce.leisureguardian.ui.devices

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
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.ac.kumoh.ce.leisureguardian.R

class DevicesFragment : Fragment() {
    private lateinit var devicesViewModel: DevicesViewModel
    private val mAdapter = RecyclerAdapter()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        devicesViewModel = ViewModelProvider(activity as AppCompatActivity).get(DevicesViewModel::class.java)
        devicesViewModel.list.observe(viewLifecycleOwner, {
            Handler(Looper.getMainLooper()).postDelayed({
                devicesViewModel.updateStatus()
                Log.d("test-Device update", "device data updated")
            }, 5000L)
            mAdapter.notifyDataSetChanged()
        })

        val root = inflater.inflate(R.layout.fragment_devices, container, false)
        val recyclerView = root.findViewById<RecyclerView>(R.id.rvDevices)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            itemAnimator = DefaultItemAnimator()
            adapter = mAdapter
        }
        return root
    }

    inner class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.DevicesViewHolder>() {
        inner class DevicesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val cardView: CardView = itemView.findViewById(R.id.cardView)
            val deviceImage: ImageView = itemView.findViewById(R.id.deviceImage)
            val deviceName: TextView = itemView.findViewById(R.id.deviceName)
            val batteryLevel: TextView = itemView.findViewById(R.id.batteryLevel)
            val temp: TextView = itemView.findViewById(R.id.temp)
            val accelMax: TextView = itemView.findViewById(R.id.accelMax)
            val heartRate: TextView = itemView.findViewById(R.id.heartRate)
        }

        override fun getItemCount(): Int = devicesViewModel.getSize()

        override fun onBindViewHolder(devicesHolder: RecyclerAdapter.DevicesViewHolder, position: Int) {
            if(devicesViewModel.getStatus(position).critical == "0" && devicesViewModel.getStatus(position).button == "0") {
                devicesHolder.deviceImage.setImageResource(R.drawable.device_green)
                devicesHolder.cardView.setCardBackgroundColor(Color.parseColor("#ffffff"))
            }
            else if(devicesViewModel.getStatus(position).critical == "1") {
                devicesHolder.deviceImage.setImageResource(R.drawable.device_black)
                devicesHolder.cardView.setCardBackgroundColor(Color.parseColor("#bebebe"))
            }
            else if(devicesViewModel.getStatus(position).critical == "2" || devicesViewModel.getStatus(position).button == "1"){
                devicesHolder.deviceImage.setImageResource(R.drawable.device_red)
                devicesHolder.cardView.setCardBackgroundColor(Color.parseColor("#ffe4e1"))
            }
            else if(devicesViewModel.getStatus(position).critical == "3") {
                devicesHolder.deviceImage.setImageResource(R.drawable.device_yellow)
                devicesHolder.cardView.setCardBackgroundColor(Color.parseColor("#eedd82"))
            }
            devicesHolder.deviceName.text = devicesViewModel.getStatus(position).deviceName
            devicesHolder.batteryLevel.text = "배터리: " + devicesViewModel.getStatus(position).batteryLevel + "%"
            devicesHolder.temp.text = "체온: " + devicesViewModel.getStatus(position).temp + "℃"
            devicesHolder.accelMax.text = "가속도: " + devicesViewModel.getStatus(position).accelMax
            devicesHolder.heartRate.text = "심박수: " + devicesViewModel.getStatus(position).heartRate
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
                RecyclerAdapter.DevicesViewHolder {
            val view = layoutInflater.inflate(R.layout.list_devices, parent, false)
            return DevicesViewHolder(view)
        }
    }
}