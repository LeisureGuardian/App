package kr.ac.kumoh.ce.leisureguardian.ui.map

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
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
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kr.ac.kumoh.ce.leisureguardian.R

class MapFragment : Fragment() {

    private lateinit var mapViewModel: MapViewModel
    private var markerList: ArrayList<Marker> = ArrayList()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        mapViewModel = ViewModelProvider(activity as AppCompatActivity).get(MapViewModel::class.java)
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    private val kumoh = LatLng(36.1455, 128.3925)

    private val callback = OnMapReadyCallback { googleMap ->
        for(i in 0 until(mapViewModel.getSize() - 1)){
            markerList.add(googleMap.addMarker(
                MarkerOptions()
                    .position(LatLng(mapViewModel.getStatus(i).latitude.toDouble(), mapViewModel.getStatus(i).longitude.toDouble()))
                    .title(mapViewModel.getStatus(i).deviceName)
                    .icon(BitmapDescriptorFactory.defaultMarker(
                        if(mapViewModel.getStatus(i).critical == "0" && mapViewModel.getStatus(i).button == "0") {
                            BitmapDescriptorFactory.HUE_GREEN
                        }
                        else {
                            BitmapDescriptorFactory.HUE_RED
                        })
                    )
            ))
        }
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(kumoh))
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15f))
        googleMap.setPadding(0, 0, 0, 120)
        googleMap.uiSettings.isZoomControlsEnabled = true
        googleMap.uiSettings.isMapToolbarEnabled = false

        mapViewModel.list.observe(viewLifecycleOwner, {
            Handler(Looper.getMainLooper()).postDelayed({
                mapViewModel.updateStatus()
                for(i in 0 until(mapViewModel.getSize() - 1)){
                    markerList[i].remove()
                    markerList.add(googleMap.addMarker(
                        MarkerOptions()
                            .position(LatLng(mapViewModel.getStatus(i).latitude.toDouble(), mapViewModel.getStatus(i).longitude.toDouble()))
                            .title(mapViewModel.getStatus(i).deviceName)
                            .icon(BitmapDescriptorFactory.defaultMarker(
                                if(mapViewModel.getStatus(i).critical == "0" && mapViewModel.getStatus(i).button == "0") {
                                    BitmapDescriptorFactory.HUE_GREEN
                                }
                                else {
                                    BitmapDescriptorFactory.HUE_RED
                                })
                            )
                    ))
                }
                Log.d("test-update", "map data updated")
            },5000L)
        })
    }
}