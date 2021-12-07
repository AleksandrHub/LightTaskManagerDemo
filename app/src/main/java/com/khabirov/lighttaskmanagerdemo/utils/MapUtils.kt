package com.niww.lighttaskmanager.utils

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.niww.lighttaskmanager.R
import com.niww.lighttaskmanager.view.fragments.MapsFragment
import java.io.IOException

object MapUtils{
    const val GOOGLE_MAP_PACKAGE = "com.google.android.apps.maps"
    const val DEFAULT_ZOOM_VALUE = 17
    const val MSG_NO_DATA = "No data"

    fun isPackageInstalled(packageName: String, packageManager: PackageManager): Boolean {
        return try {
            packageManager.getPackageInfo(packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private fun enableMyLocation(map: GoogleMap, activity: FragmentActivity) {
        // [START maps_check_location_permission]
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            map.isMyLocationEnabled = true

        } else {
            // Permission to access the location is missing. Show rationale and request permission
            requestPermission(activity, MapsFragment.LOCATION_PERMISSION_REQUEST_CODE,
                Manifest.permission.ACCESS_FINE_LOCATION, true
            )
        }
    }

    fun checkLocationPermission(activity: Activity) {
        if (ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 100
            )
        }
    }

    /**
     * Requests the fine location permission. If a rationale with an additional explanation should
     * be shown to the user, displays a dialog that triggers the request.
     */
    @JvmStatic
    fun requestPermission(
        activity: FragmentActivity, requestId: Int,
        permission: String, finishActivity: Boolean
    ) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
            // Display a dialog with rationale.
            RationaleDialog.newInstance(requestId, finishActivity)
                .show(activity.supportFragmentManager, "dialog")
        } else {
            // Location permission has not been granted yet, request it.
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(permission),
                requestId
            )
        }
    }

    class RationaleDialog : DialogFragment() {
        private var finishActivity = false
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            val requestCode =
                arguments?.getInt(ARGUMENT_PERMISSION_REQUEST_CODE) ?: 0
            finishActivity =
                arguments?.getBoolean(ARGUMENT_FINISH_ACTIVITY) ?: false
            return AlertDialog.Builder(activity)
                .setMessage(R.string.permission_rationale_location)
                .setPositiveButton(android.R.string.ok) { dialog, which ->
                    // After click on Ok, request the permission.
                    ActivityCompat.requestPermissions(
                        requireActivity(),
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        requestCode
                    )
                    // Do not finish the Activity while requesting permission.
                    finishActivity = false
                }
                .setNegativeButton(android.R.string.cancel, null)
                .create()
        }

        override fun onDismiss(dialog: DialogInterface) {
            super.onDismiss(dialog)
            if (finishActivity) {
                Toast.makeText(
                    activity,
                    R.string.permission_required_toast,
                    Toast.LENGTH_SHORT
                ).show()
                activity?.finish()
            }
        }

        companion object {
            private const val ARGUMENT_PERMISSION_REQUEST_CODE = "requestCode"
            private const val ARGUMENT_FINISH_ACTIVITY = "finish"

            /**
             * Creates a new instance of a dialog displaying the rationale for the use of the location
             * permission.
             *
             * @param requestCode    Id of the request that is used to request the permission. It is
             * returned to the
             * [androidx.core.app.ActivityCompat.OnRequestPermissionsResultCallback].
             * @param finishActivity Whether the calling Activity should be finished if the dialog is
             * cancelled.
             */
            fun newInstance(requestCode: Int, finishActivity: Boolean): RationaleDialog {
                val arguments = Bundle().apply {
                    putInt(ARGUMENT_PERMISSION_REQUEST_CODE, requestCode)
                    putBoolean(ARGUMENT_FINISH_ACTIVITY, finishActivity)
                }
                return RationaleDialog().apply {
                    this.arguments = arguments
                }
            }
        }
    }

    internal fun getAddressByCoordinates(latLng: LatLng, activity: FragmentActivity): String? {

        // Create geocoder
        val geocoder = Geocoder(activity)
        val latitude = latLng.latitude
        val longitude = latLng.longitude

        // Try to get addresses list
        val list: List<Address>
        list = try {
            geocoder.getFromLocation(latitude, longitude, 1)
        } catch (e: IOException) {
            e.printStackTrace()
            return e.localizedMessage!!
        }

        // If list is empty, return "No data" string
        if (list.isEmpty()) return MSG_NO_DATA

        // Get first element from List
        val a = list[0]

        // Get a Postal Code
        val index = a.maxAddressLineIndex
        var postal: String? = null

        if (a.thoroughfare != null && index >= 0) {
            postal = a.getAddressLine(index)
        } else if (index >= 0) {

            val builder = StringBuilder()
            val sep = ", "
            builder.append(latitude.toString()).append(sep).append(longitude.toString())
            postal = builder.toString()
        }

        return postal
    }

    internal fun mapRefresh(
        googleMap: GoogleMap,
        marker: MarkerOptions,
        position: LatLng,
        currentZoomValue: Float
    ) {
        googleMap.clear()
        googleMap.addMarker(marker)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, currentZoomValue))
    }

    internal fun mapUiSetup(googleMap: GoogleMap, activity: FragmentActivity){
        enableMyLocation(googleMap, activity)
        googleMap.uiSettings.isMyLocationButtonEnabled = true
        googleMap.uiSettings.isCompassEnabled = true
        googleMap.uiSettings.isMapToolbarEnabled = true
    }

}







