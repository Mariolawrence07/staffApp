package com.example.staffapp

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.DirectionsApi
import com.google.maps.GeoApiContext
import com.google.maps.errors.ApiException
import com.google.maps.model.DirectionsResult
import com.google.maps.model.TravelMode
import java.io.IOException
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.graphics.Color
import android.location.Location
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.PolylineOptions


class MapActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var map: GoogleMap
    private lateinit var startLocation: LatLng
    private lateinit var endLocation: LatLng
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val LOCATION_PERMISSION_REQUEST_CODE = 1
    private lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        endLocation = LatLng(53.010407, -2.180152)
        startLocation = LatLng(53.003562, -2.186896)

        // Set up the map fragment
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        drawLineOnMap()
    }

    private fun drawLineOnMap() {

        map.addPolyline(
            PolylineOptions()
                .add(startLocation, endLocation)
                .width(10f)
                .color(Color.RED)
        )

        map.addMarker(MarkerOptions().position(startLocation).title("Start Location"))
        map.addMarker(MarkerOptions().position(endLocation).title("End Location"))

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(startLocation, 15f))
    }

    private fun getDirections(startLocation: LatLng, endLocation: LatLng): DirectionsResult? {
        val context = GeoApiContext.Builder()
            .apiKey("AIzaSyBW_9c6jJ5ke7D-3Y-ArJFDrvtl3hp5LNc")
            .build()

        try {
            return DirectionsApi.newRequest(context)
                .mode(TravelMode.WALKING)
                .origin(com.google.maps.model.LatLng(startLocation.latitude, startLocation.longitude))
                .destination(com.google.maps.model.LatLng(endLocation.latitude, endLocation.longitude))
                .await()
        } catch (e: ApiException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return null
    }

    private fun getLastLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    // Handle the retrieved location
                    val latitude = location.latitude
                    val longitude = location.longitude
                    // ...
                }
            }
            .addOnFailureListener { exception: Exception ->
                // Handle the failure (e.g., location services disabled, no last known location available)
                // ...
            }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
                // Proceed to location retrieval
                getLastLocation()
            } else {
                // Permission denied
                // Handle accordingly (e.g., show an error message)
            }
        }
    }


}