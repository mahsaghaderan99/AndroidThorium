package com.example.thorium_android

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.Typeface
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thorium_android.R
import com.example.thorium_android.adapters.LocatopnListAdapter
import com.example.thorium_android.entities.Cell
import com.example.thorium_android.entities.LocData
import com.example.thorium_android.view_models.LocationViewModel

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_second.view.*
import kotlinx.coroutines.yield

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var locationViewModel: LocationViewModel
    private var activeMarkers = mutableListOf<Marker>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_map)
        locationViewModel = ViewModelProvider(this).get(LocationViewModel::class.java)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.isMyLocationEnabled = true
        mMap.setInfoWindowAdapter(object : GoogleMap.InfoWindowAdapter {
            override fun getInfoWindow(arg0: Marker): View? {
                return null
            }
            override fun getInfoContents(marker: Marker): View {
                val info = LinearLayout(applicationContext)
                info.orientation = LinearLayout.VERTICAL
                val title = TextView(applicationContext)
                title.setTextColor(Color.BLACK)
                title.gravity = Gravity.CENTER
                title.setTypeface(null, Typeface.BOLD)
                title.text = marker.title
                val snippet = TextView(applicationContext)
                snippet.setTextColor(Color.GRAY)
                snippet.text = marker.snippet
                info.addView(title)
                info.addView(snippet)
                return info
            }
        })
        Log.d("ADebugTag", "Before find all location");

        locationViewModel.allCellWithLocations.observe(this, Observer { allCells ->
            // Update the list of markers
            Log.d("ADebugTag", "Finding each location");
            allCells?.let { showBaseStatioMarkers() }
        })
    }

    fun showBaseStatioMarkers() {

        for(marker in activeMarkers) {
            marker.remove()
        }

        val allData = locationViewModel.allCellWithLocations.value
        val cellMap = mutableMapOf<String,  MutableList<LocData>>()
        Log.d("ADebugTag", "All location date lenght = "+ allData!!.size.toString());
        if (allData != null) {
            for (data in allData.iterator()) {
                Log.d("ADebugTag", "Ech location in loop" + data.cell.cid.toString());
                val locations = data.locData
                for (location in locations) {
                    if (cellMap.containsKey(data.cell.cid)){
                        cellMap[data.cell.cid]!!.add(location)

                    } else {
                        cellMap.put(data.cell.cid, mutableListOf())
                        cellMap[data.cell.cid]!!.add(location)
                    }
                }
            }
        }

        cellMap.entries.forEachIndexed { index, mutableEntry ->
            val cid: Double = mutableEntry.key.toDouble()
            val x = mutableEntry.value.get(index).latitude
            Log.d("ADebugTag", "The location is......"+ cid.toString() + "...." + x.toString());
            val y = mutableEntry.value.get(index).longitude
            val pos = LatLng(x, y)
            val color = getColorz(index)
            val snip = "Cell: $cid"

            val marker = mMap.addMarker(MarkerOptions().icon(
                    BitmapDescriptorFactory.defaultMarker(color)).position(
                    pos).title(cid.toString()).snippet(snip))

            activeMarkers.add(marker)
        }
    }

    fun getColorz(index: Int): Float {
        val colors = listOf(BitmapDescriptorFactory.HUE_AZURE, BitmapDescriptorFactory.HUE_GREEN, BitmapDescriptorFactory.HUE_ORANGE, BitmapDescriptorFactory.HUE_ROSE, BitmapDescriptorFactory.HUE_YELLOW)
        return colors[index];
    }

}