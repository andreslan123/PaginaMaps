package com.example.appubicacion

<<<<<<< HEAD
import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
=======
// CAMBIO CLAVE: Importamos el R del namespace que Firebase espera
import com.example.miubicacionapp.R
import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
>>>>>>> 519863f09e045cd16a2e49267040f1714673e938
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
<<<<<<< HEAD
import com.example.miubicacionapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*
=======
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.FirebaseDatabase
>>>>>>> 519863f09e045cd16a2e49267040f1714673e938
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.animation.flyTo
import com.mapbox.maps.plugin.locationcomponent.location

class MainActivity : AppCompatActivity() {

    private lateinit var mapView: MapView
    private lateinit var txtCoordenadas: TextView
    private lateinit var btnCentrar: FloatingActionButton
<<<<<<< HEAD

    // Referencia base a Firebase
    private val database = FirebaseDatabase.getInstance().reference
=======
    private val database = FirebaseDatabase.getInstance().getReference("ubicaciones")
>>>>>>> 519863f09e045cd16a2e49267040f1714673e938

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
<<<<<<< HEAD
            configurarAlertaRemota()
=======
>>>>>>> 519863f09e045cd16a2e49267040f1714673e938
        }

        btnCentrar.setOnClickListener {
            val locationComponent = mapView.location
<<<<<<< HEAD
            val listener = com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener { point ->
                val cameraOptions = CameraOptions.Builder().center(point).zoom(16.0).build()
                mapView.mapboxMap.flyTo(cameraOptions)
            }
            locationComponent.addOnIndicatorPositionChangedListener(listener)
=======
            // Listener temporal para obtener la posición actual y volar hacia ella
            val listener = com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener { point ->
                val cameraOptions = CameraOptions.Builder()
                    .center(point)
                    .zoom(16.0)
                    .build()
                mapView.mapboxMap.flyTo(cameraOptions)
            }
            locationComponent.addOnIndicatorPositionChangedListener(listener)
            // Lo removemos tras un segundo para que no bloquee la cámara
>>>>>>> 519863f09e045cd16a2e49267040f1714673e938
            btnCentrar.postDelayed({ locationComponent.removeOnIndicatorPositionChangedListener(listener) }, 1000)
        }
    }

<<<<<<< HEAD
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

=======
>>>>>>> 519863f09e045cd16a2e49267040f1714673e938
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
<<<<<<< HEAD
                // Enviamos a la ruta exacta: ubicaciones/usuario1
                database.child("ubicaciones").child("usuario1").setValue(datos)
=======
                database.child("usuario1").setValue(datos)
>>>>>>> 519863f09e045cd16a2e49267040f1714673e938
            }
        }
    }

    override fun onStart() { super.onStart(); mapView.onStart() }
    override fun onStop() { super.onStop(); mapView.onStop() }
    override fun onDestroy() { super.onDestroy(); mapView.onDestroy() }
<<<<<<< HEAD
}
=======
} // <-- Esta llave cerraba todo el código
>>>>>>> 519863f09e045cd16a2e49267040f1714673e938
