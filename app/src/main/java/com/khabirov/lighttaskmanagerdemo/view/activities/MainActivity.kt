package com.niww.lighttaskmanager.view.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.niww.lighttaskmanager.R
import com.niww.lighttaskmanager.utils.MapUtils.GOOGLE_MAP_PACKAGE
import com.niww.lighttaskmanager.utils.MapUtils.checkLocationPermission
import com.niww.lighttaskmanager.utils.MapUtils.isPackageInstalled

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // For development only. To be removed.
        if (getString(R.string.maps_api_key).isEmpty()) {
            Toast.makeText(this, "Add your own API key in ../secure.properties as MAPS_API_KEY=YOUR_API_KEY", Toast.LENGTH_LONG).show()
        }

        isPackageInstalled(GOOGLE_MAP_PACKAGE, packageManager)
        checkLocationPermission(this)
    }

}