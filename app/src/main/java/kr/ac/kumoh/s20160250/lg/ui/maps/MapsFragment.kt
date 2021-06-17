package kr.ac.kumoh.s20160250.lg.ui.maps

import kr.ac.kumoh.s20160250.lg.R

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kr.ac.kumoh.s20160250.lg.ui.list.ListViewModel


class MapsFragment : Fragment() {

    private val sg204 = LatLng(36.1455, 128.3925)
    private lateinit var mapviewmodel: MapsViewModel

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

        for(i in 0 until(mapviewmodel.getSize() - 1)){
            googleMap.addMarker(
                MarkerOptions()
                    .position(LatLng(mapviewmodel.getStatus(i).latitude.toDouble(), mapviewmodel.getStatus(i).longitude.toDouble()))
                    .title(mapviewmodel.getStatus(i).deviceName)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
            )
        }
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sg204))
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15f))
        googleMap.uiSettings.isZoomControlsEnabled = true
        googleMap.uiSettings.isMapToolbarEnabled = false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mapviewmodel = ViewModelProvider(activity as AppCompatActivity).get(MapsViewModel::class.java)
        Log.d("test/mapfragment",mapviewmodel.list.value.toString())
        return inflater.inflate(R.layout.fragment_maps, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

}