package com.example.appubicacion

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.miubicacionapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.animation.flyTo
import com.mapbox.maps.plugin.locationcomponent.location

class MainActivity : AppCompatActivity() {

    private lateinit var mapView: MapView
    private lateinit var txtCoordenadas: TextView
    private lateinit var btnCentrar: FloatingActionButton

    // Referencia base a Firebase
    private val database = FirebaseDatabase.getInstance().reference

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) enableLocation() else Toast.makeText(this, "Permiso denegado", Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mapView = findViewById(R.id.mapView)
        txtCoordenadas = findViewById(R.id.txtCoordenadas)
        btnCentrar = findViewById(R.id.btnCentrar)

        mapView.mapboxMap.loadStyle(Style.MAPBOX_STREETS) {
            checkPermissions()
            configurarAlertaRemota()
        }

        btnCentrar.setOnClickListener {
            val locationComponent = mapView.location
            val listener = com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener { point ->
                val cameraOptions = CameraOptions.Builder().center(point).zoom(16.0).build()
                mapView.mapboxMap.flyTo(cameraOptions)
            }
            locationComponent.addOnIndicatorPositionChangedListener(listener)
            btnCentrar.postDelayed({ locationComponent.removeOnIndicatorPositionChangedListener(listener) }, 1000)
        }
    }

    private fun configurarAlertaRemota() {
        // Escucha si la web cambia el estado a true
        database.child("estado").child("alerta").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val alertaActiva = snapshot.getValue(Boolean::class.java) ?: false
                if (alertaActiva) {
                    val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                    vibrator.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE))
                    Toast.makeText(this@MainActivity, "¡ALERTA: Fuera de la geocerca!", Toast.LENGTH_LONG).show()
                }
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            enableLocation()
        } else {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun enableLocation() {
        mapView.location.apply {
            enabled = true
            pulsingEnabled = true
            addOnIndicatorPositionChangedListener { point ->
                val lat = point.latitude()
                val lng = point.longitude()
                txtCoordenadas.text = "Lat: $lat\nLng: $lng"

                val datos = mapOf(
                    "latitud" to lat,
                    "longitud" to lng,
                    "timestamp" to System.currentTimeMillis()
                )
                // Enviamos a la ruta exacta: ubicaciones/usuario1
                database.child("ubicaciones").child("usuario1").setValue(datos)
            }
        }
    }

    override fun onStart() { super.onStart(); mapView.onStart() }
    override fun onStop() { super.onStop(); mapView.onStop() }
    override fun onDestroy() { super.onDestroy(); mapView.onDestroy() }
}