package com.example.thorium_android

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thorium_android.adapters.LocatopnListAdapter
import com.example.thorium_android.entities.Cell
import com.example.thorium_android.entities.LocData
import com.example.thorium_android.view_models.LocationViewModel
import kotlinx.android.synthetic.main.fragment_second.view.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private lateinit var locationViewModel: LocationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_second, container, false)
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // RecyclerView
        val adapter = LocatopnListAdapter()
        val recyclerView = view.recyclerview

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        locationViewModel = ViewModelProvider(this).get(LocationViewModel::class.java)
        locationViewModel.allCellWithLocations.observe(viewLifecycleOwner, Observer {
            var dataList: MutableList<Pair<LocData, Cell>> = mutableListOf()
            for (cellWithLoc in it) {
                for (loc in cellWithLoc.locData) {
                    dataList.add(Pair(loc, cellWithLoc.cell))
                }
            }

            Log.e("AAAA", "onViewCreated: $dataList")
            adapter.submitList(dataList.map { data -> data })
            // adapter.notifyDataSetChanged()
        })

        view.findViewById<Button>(R.id.button_second).setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }
}