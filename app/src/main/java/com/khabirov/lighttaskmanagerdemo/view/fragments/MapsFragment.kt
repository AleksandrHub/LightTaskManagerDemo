package com.niww.lighttaskmanager.view.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.niww.lighttaskmanager.R
import com.niww.lighttaskmanager.R.layout
import com.niww.lighttaskmanager.utils.MapUtils.DEFAULT_ZOOM_VALUE
import com.niww.lighttaskmanager.utils.MapUtils.getAddressByCoordinates
import com.niww.lighttaskmanager.utils.MapUtils.mapRefresh
import com.niww.lighttaskmanager.utils.MapUtils.mapUiSetup
import kotlinx.android.synthetic.main.fragment_maps.view.*

class MapsFragment : Fragment() {

    private var mapTextView: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(layout.fragment_maps, container, false)
        initUi(root)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    private fun initUi(view: View){
        mapTextView = view.findViewById(R.id.TextViewMap)
        view.fragment_map_done_icon.setOnClickListener(){
            findNavController().navigate(R.id.navigation_fragment_maps_to_fragment_new_task)
        }

        view.fragment_map_arrow_back_icon.setOnClickListener(){
            findNavController().navigate(R.id.navigation_fragment_maps_to_fragment_new_task)
        }
    }

    @SuppressLint("MissingPermission")
    private val callback = OnMapReadyCallback{ googleMap ->

        val activity = requireActivity()
        mapUiSetup(googleMap, activity)

        // Location manager
        val mLocManager: LocationManager = activity.
            getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val loc: Location?

        // Receive information from GPS provider
        loc = mLocManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

        val marker = MarkerOptions()
        var latitude = loc.latitude
        var longitude = loc.longitude
        var position = LatLng(latitude, longitude)

        googleMap.setOnMapClickListener {
            val currentZoomValue = googleMap.cameraPosition.zoom
            position = it
            latitude = position.latitude
            longitude = position.longitude
            marker.position(it)
            marker.title(getString(R.string.map_new_position))
            mapRefresh(googleMap, marker, position, currentZoomValue)
            mapTextView?.text = getAddressByCoordinates(position, activity)
        }

        marker.position(LatLng(latitude, longitude))
        marker.title("Current position");
        mapRefresh(googleMap, marker, position, DEFAULT_ZOOM_VALUE.toFloat())
        mapTextView?.text = getAddressByCoordinates(position, activity)
    }

    companion object {
        /**
         * Request code for location permission request.
         *
         * @see .onRequestPermissionsResult
         */
        internal const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }
}