package com.example.thorium_android

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.Typeface
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.CellInfo
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
    inner class NetCell(var cell: Cell, val location: LocData)
    val color_map = mutableMapOf<Int, Int>()
    var next_color : Int = 0


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
            allCells?.let { showBaseStatioMarkers("CID") }
        })
    }

    fun showBaseStatioMarkers(coloring_method: String) {

        for(marker in activeMarkers) {
            marker.remove()
        }

        val allData = locationViewModel.allCellWithLocations.value
        val cellMap = mutableMapOf<String,  MutableList<NetCell>>()
        if (allData != null) {
            for (data in allData.iterator()) {
                val locations = data.locData
                for (location in locations) {
                    if (cellMap.containsKey(data.cell.cid)){
                        //Measurement(data.strength.toDouble(), data.altitude, data.longitude)
                        cellMap[data.cell.cid]!!.add(NetCell(data.cell,location))

                    } else {
                        cellMap.put(data.cell.cid, mutableListOf())
                        cellMap[data.cell.cid]!!.add(NetCell(data.cell,location))
                    }
                }
            }
        }

        cellMap.entries.forEachIndexed { index, mutableEntry ->
            val cid: Double = mutableEntry.key.toDouble()
            val x = mutableEntry.value.get(index).location.latitude
            val y = mutableEntry.value.get(index).location.longitude
            val pos = LatLng(x, y)
            val color = getColorz(index,mutableEntry.value.get(index).cell,coloring_method)
            val snip = "Cell: $cid"

            val marker = mMap.addMarker(MarkerOptions().icon(
                    BitmapDescriptorFactory.defaultMarker(color)).position(
                    pos).title(cid.toString()).snippet(snip))

            activeMarkers.add(marker)
        }
    }

    fun getColorz(index: Int, cell: Cell, coloring_method: String): Float {
        val colors = listOf(
                BitmapDescriptorFactory.HUE_AZURE,
                BitmapDescriptorFactory.HUE_GREEN,
                BitmapDescriptorFactory.HUE_ORANGE,
                BitmapDescriptorFactory.HUE_ROSE,
                BitmapDescriptorFactory.HUE_YELLOW,
                BitmapDescriptorFactory.HUE_BLUE,
                BitmapDescriptorFactory.HUE_CYAN,
                BitmapDescriptorFactory.HUE_MAGENTA,
                BitmapDescriptorFactory.HUE_RED,
                BitmapDescriptorFactory.HUE_VIOLET
        )
        var len_colors = 10
        var param = 0
        if (coloring_method == "CID"){
            param = cell.cid.toInt()
        }
        if(coloring_method == "type"){
            val cell_type = cell.cellType
            if(cell_type == "GSM"){//blue
                return BitmapDescriptorFactory.HUE_BLUE
            }else if(cell_type=="UMTS"){//yellow
                return BitmapDescriptorFactory.HUE_YELLOW
            }else if (cell_type=="LTE"){//orange
                return BitmapDescriptorFactory.HUE_ORANGE
            }else{//reg
                return BitmapDescriptorFactory.HUE_RED
            }
        }
        else if(coloring_method=="PLMN"){
            param = (cell.mcc+cell.mnc).toInt()
        }
        else if(coloring_method=="TAC"){
            param = cell.lac_tac.toInt()
        }
        else if (coloring_method == "ARFCN"){
            param = cell.arfcn.toInt()
        }
        var cur_color = 0
        if (color_map.containsKey(param)){
            cur_color = color_map[param]!!

        } else {
            color_map.put(param, next_color)
            val cur_color = next_color
            next_color = (next_color + 1)%len_colors
        }
        return colors[cur_color];
    }
}