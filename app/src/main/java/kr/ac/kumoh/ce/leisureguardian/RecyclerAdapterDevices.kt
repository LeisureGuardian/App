package kr.ac.kumoh.ce.leisureguardian

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kr.ac.kumoh.ce.leisureguardian.data.StatusData
import kotlinx.android.synthetic.main.list_devices.view.*

class RecyclerAdapterDevices(private val devices: ArrayList<StatusData>) :
    RecyclerView.Adapter<RecyclerAdapterDevices.ViewHolder>() {

    override fun getItemCount(): Int = devices.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val device = devices[position]
        val listener = View.OnClickListener {
            Toast.makeText(it.context, "Clicked: " + device.deviceSerial, Toast.LENGTH_SHORT).show()
        }
        holder.apply {
            bind(listener, device)
            itemView.tag = device
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ViewHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.list_devices, parent, false)
        return ViewHolder(inflatedView)
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var view: View = v
        fun bind(listener: View.OnClickListener, device: StatusData) {
            view.deviceImage.setImageResource(R.drawable.device_black)
            view.deviceSerial.text = device.deviceSerial
            view.critical.text = device.critical
            view.batteryLevel.text = device.batteryLevel
            view.temp.text = device.temp
            view.accelMax.text = device.accelMax
            view.heartRate.text = device.heartRate
            view.setOnClickListener(listener)
        }
    }

}