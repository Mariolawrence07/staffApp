package com.example.staffapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.example.staffapp.database.ServiceBuilder
import com.example.staffapp.database.WeatherService
import com.example.staffapp.databinding.ActivityWeaatherBinding
import com.example.staffapp.model.Weather
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeaatherActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivityWeaatherBinding
    private lateinit var map: GoogleMap
    private lateinit var startLocation: LatLng
    private lateinit var endLocation: LatLng
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var mapView: MapView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeaatherBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.progressBar.visibility = View.VISIBLE

        loadData()

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        endLocation = LatLng(53.010407, -2.180152)

        // Set up the map fragment
        mapView = findViewById(R.id.mapView)
        mapView.getMapAsync(this)
    }

    private fun loadData() {
        val service  = ServiceBuilder.buildService(WeatherService::class.java)
        val requestCall = service.getWeather()

        requestCall.enqueue(object : Callback<Weather> {

            override fun onResponse(call: Call<Weather>,
                                    response: Response<Weather>
            ) {

                if (response.isSuccessful){
                    //process data
                    val weather  = response.body()!!

                    binding.txtName.text = weather.name
                    binding.txtTemp.text = weather.main.temp.toString()
                    binding.txtDescription.text = weather.weather[0].description
                    Picasso.get().load("https://openweathermap.org/img/w/${weather.weather[0].icon}.png").into(binding.imgIcon)
                    binding.progressBar.visibility = View.GONE

                }else{
                    //output alert
                    AlertDialog.Builder(this@WeaatherActivity)
                        .setTitle("API error")
                        .setMessage("Response, but something went wrong ${response.message()}")
                        .setPositiveButton(android.R.string.ok) { _, _ -> }
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show()

                }
            }
            override fun onFailure(call: Call<Weather>, t: Throwable) {
                //process failure
                Log.e("WeatherService", "Error fetching weather", t)
            }
        })
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        if (googleMap != null) {
            map = googleMap

            map.addMarker(MarkerOptions().position(endLocation).title("Stoke Map"))

            map.moveCamera(CameraUpdateFactory.newLatLngZoom(startLocation, 15f))
        }
    }

}