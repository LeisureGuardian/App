package kr.ac.kumoh.s20160250.lg.ui.maps

import kr.ac.kumoh.s20160250.lg.R

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kr.ac.kumoh.s20160250.lg.MySingleton
import kr.ac.kumoh.s20160250.lg.data.StatusData
import kr.ac.kumoh.s20160250.lg.ui.list.ListFragment


class MapsFragment : Fragment() {


    private lateinit var mapviewmodel: MapsViewModel
    private val kumoh = LatLng(36.1455, 128.3925)
    private var callback = OnMapReadyCallback { googleMap ->


        for(i in 0 until(MySingleton.getInstance().markerList.size)) {
            MySingleton.getInstance().markerList[i].remove()
        }
        MySingleton.getInstance().markerList.clear()

        for(i in 0 until(MySingleton.getInstance().deviceinfo.size)){

            MySingleton.getInstance().markerList.add(googleMap.addMarker(
                MarkerOptions()
                    .position(LatLng(MySingleton.getInstance().deviceinfo[i].latitude.toDouble(), MySingleton.getInstance().deviceinfo[i].longitude.toDouble()))
                    .title(MySingleton.getInstance().deviceinfo[i].deviceName)
                    .icon(BitmapDescriptorFactory.defaultMarker(
                        if(MySingleton.getInstance().deviceinfo[i].critical == "0" && MySingleton.getInstance().deviceinfo[i].button == "0") {
                            BitmapDescriptorFactory.HUE_GREEN
                        }
                        else {
                            BitmapDescriptorFactory.HUE_ROSE
                        })
                    )
            ))
        }

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(kumoh))
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15f))
        googleMap.setPadding(0, 0, 0, 120)
        googleMap.uiSettings.isZoomControlsEnabled = true
        googleMap.uiSettings.isMapToolbarEnabled = false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mapviewmodel = ViewModelProvider(activity as AppCompatActivity).get(MapsViewModel::class.java)
        mapviewmodel.list.observe(viewLifecycleOwner, Observer<ArrayList<StatusData>> {
            Handler(Looper.getMainLooper()).postDelayed({

                mapviewmodel.update_status()
                callback= OnMapReadyCallback { googleMap ->


                    for(i in 0 until(MySingleton.getInstance().markerList.size)) {
                        MySingleton.getInstance().markerList[i].remove()
                    }
                    MySingleton.getInstance().markerList.clear()

                    for(i in 0 until(MySingleton.getInstance().deviceinfo.size)){

                        MySingleton.getInstance().markerList.add(googleMap.addMarker(
                            MarkerOptions()
                                .position(LatLng(MySingleton.getInstance().deviceinfo[i].latitude.toDouble(), MySingleton.getInstance().deviceinfo[i].longitude.toDouble()))
                                .title(MySingleton.getInstance().deviceinfo[i].deviceName)
                                .icon(BitmapDescriptorFactory.defaultMarker(
                                    if(MySingleton.getInstance().deviceinfo[i].critical == "0" && MySingleton.getInstance().deviceinfo[i].button == "0") {
                                        BitmapDescriptorFactory.HUE_GREEN
                                    }
                                    else {
                                        BitmapDescriptorFactory.HUE_ROSE
                                    })
                                )
                        ))
                    }

                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(kumoh))
                    googleMap.animateCamera(CameraUpdateFactory.zoomTo(15f))
                    googleMap.setPadding(0, 0, 0, 120)
                    googleMap.uiSettings.isZoomControlsEnabled = true
                    googleMap.uiSettings.isMapToolbarEnabled = false
                }
                val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
                mapFragment?.getMapAsync(callback)
            },5000L)
        })
        Log.d("test/mapfragment",mapviewmodel.list.value.toString())
        return inflater.inflate(R.layout.fragment_maps, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?

        mapFragment?.getMapAsync(callback)
    }
}