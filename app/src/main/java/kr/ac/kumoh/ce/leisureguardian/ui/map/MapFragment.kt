package kr.ac.kumoh.ce.leisureguardian.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kr.ac.kumoh.ce.leisureguardian.R

class MapFragment : Fragment() {
    private val KUMOH = LatLng(36.1455, 128.3925)
    private val SBMI = LatLng(36.1359, 128.3956)
    private val SG204 = LatLng(36.1395, 128.3961)

    private lateinit var markerKumoh: Marker
    private lateinit var markerSbmi: Marker
    private lateinit var markerSg204: Marker

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
        markerKumoh = googleMap.addMarker(
            MarkerOptions()
                .position(KUMOH)
                .title("Kumoh University")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
        )
        markerKumoh.tag = 0
        markerSbmi = googleMap.addMarker(
            MarkerOptions()
                .position(SBMI)
                .title("SBMI")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
        )
        markerSbmi.tag = 0
        markerSg204 = googleMap.addMarker(
            MarkerOptions()
                .position(SG204)
                .title("SG204")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
        )
        markerSg204.tag = 0

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(KUMOH))
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15f))
        googleMap.uiSettings.isZoomControlsEnabled = true
        googleMap.uiSettings.isMapToolbarEnabled = false
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}