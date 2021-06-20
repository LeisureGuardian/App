package kr.ac.kumoh.ce.leisureguardian.ui.map

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kr.ac.kumoh.ce.leisureguardian.R
import kr.ac.kumoh.ce.leisureguardian.Singleton

class MapFragment : Fragment() {

    private var mapFragment: SupportMapFragment ?= null
    private lateinit var mapViewModel: MapViewModel
    private val kumoh = LatLng(36.1455, 128.3925)
    private var callback = OnMapReadyCallback { googleMap ->
        for (i in 0 until (Singleton.getInstance().markerList.size)) {
            Singleton.getInstance().markerList[i].remove()
        }
        Singleton.getInstance().markerList.clear()
        for (i in 0 until (Singleton.getInstance().deviceInfo.size)) {
            if(Singleton.getInstance().deviceInfo[i].critical == "1") {
                Singleton.getInstance().markerList.add(googleMap.addMarker(
                    MarkerOptions()
                        .position(LatLng(
                            Singleton.getInstance().deviceInfo[i].latitude.toDouble(),
                            Singleton.getInstance().deviceInfo[i].longitude.toDouble()
                        ))
                        .title(Singleton.getInstance().deviceInfo[i].deviceName)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_gray)
                        ))
                )
            }
            else {
                Singleton.getInstance().markerList.add(googleMap.addMarker(
                    MarkerOptions()
                        .position(LatLng(
                            Singleton.getInstance().deviceInfo[i].latitude.toDouble(),
                            Singleton.getInstance().deviceInfo[i].longitude.toDouble()
                        ))
                        .title(Singleton.getInstance().deviceInfo[i].deviceName)
                        .icon(BitmapDescriptorFactory.defaultMarker(
                            if(Singleton.getInstance().deviceInfo[i].critical == "2" || Singleton.getInstance().deviceInfo[i].button == "1") {
                                BitmapDescriptorFactory.HUE_RED
                            }
                            else if(Singleton.getInstance().deviceInfo[i].critical == "3") {
                                BitmapDescriptorFactory.HUE_YELLOW
                            }
                            else {
                                BitmapDescriptorFactory.HUE_GREEN
                            }
                        ))
                ))
            }
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
        mapViewModel = ViewModelProvider(activity as AppCompatActivity).get(MapViewModel::class.java)
        mapViewModel.list.observe(viewLifecycleOwner, {
            Handler(Looper.getMainLooper()).postDelayed({
                mapViewModel.updateStatus()
                callback = OnMapReadyCallback { googleMap ->
                    for (i in 0 until (Singleton.getInstance().markerList.size)) {
                        Singleton.getInstance().markerList[i].remove()
                    }
                    Singleton.getInstance().markerList.clear()
                    for (i in 0 until (Singleton.getInstance().deviceInfo.size)) {
                        if(Singleton.getInstance().deviceInfo[i].critical == "1") {
                            Singleton.getInstance().markerList.add(googleMap.addMarker(
                                MarkerOptions()
                                    .position(LatLng(
                                        Singleton.getInstance().deviceInfo[i].latitude.toDouble(),
                                        Singleton.getInstance().deviceInfo[i].longitude.toDouble()
                                    ))
                                    .title(Singleton.getInstance().deviceInfo[i].deviceName)
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_gray)
                                    ))
                            )
                        }
                        else {
                            Singleton.getInstance().markerList.add(googleMap.addMarker(
                                MarkerOptions()
                                    .position(LatLng(
                                        Singleton.getInstance().deviceInfo[i].latitude.toDouble(),
                                        Singleton.getInstance().deviceInfo[i].longitude.toDouble()
                                    ))
                                    .title(Singleton.getInstance().deviceInfo[i].deviceName)
                                    .icon(BitmapDescriptorFactory.defaultMarker(
                                        if(Singleton.getInstance().deviceInfo[i].critical == "2" || Singleton.getInstance().deviceInfo[i].button == "1") {
                                            BitmapDescriptorFactory.HUE_RED
                                        }
                                        else if(Singleton.getInstance().deviceInfo[i].critical == "3") {
                                            BitmapDescriptorFactory.HUE_YELLOW
                                        }
                                        else {
                                            BitmapDescriptorFactory.HUE_GREEN
                                        }
                                    ))
                            ))
                        }
                    }
                }
                mapFragment?.getMapAsync(callback)
            }, 5000L)
        })
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}