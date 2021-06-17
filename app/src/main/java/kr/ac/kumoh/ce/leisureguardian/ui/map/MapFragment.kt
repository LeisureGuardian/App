package kr.ac.kumoh.ce.leisureguardian.ui.map

import android.os.Bundle
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

class MapFragment : Fragment() {

    private lateinit var mapViewModel: MapViewModel

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
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */

        for(i in 0 until(mapViewModel.getSize() - 1)){
            googleMap.addMarker(
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
            )
        }
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(kumoh))
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15f))
        googleMap.uiSettings.isZoomControlsEnabled = true
        googleMap.uiSettings.isMapToolbarEnabled = false
    }
}