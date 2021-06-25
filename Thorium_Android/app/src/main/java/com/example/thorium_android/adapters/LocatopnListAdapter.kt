package com.example.thorium_android.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.thorium_android.R
import com.example.thorium_android.entities.Cell
import com.example.thorium_android.entities.LocData
import kotlinx.android.synthetic.main.trace_recyclerview_item.view.*

class LocatopnListAdapter :
    ListAdapter<Pair<LocData, Cell>, LocatopnListAdapter.MyViewHolder>(DiffUtil) {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.trace_recyclerview_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = currentList[position].first
        val cell = currentList[position].second
        holder.itemView.type.text = cell!!.cellType
        holder.itemView.time.text = currentItem.time.toString()
        holder.itemView.altitude.text = currentItem.latitude.toString()
        holder.itemView.longitude.text = currentItem.longitude.toString()
        holder.itemView.mcc.text = cell!!.mcc
        holder.itemView.mnc.text = cell!!.mnc
        holder.itemView.lac.text = cell!!.lac_tac
        Log.d("Heey", "sdad");
    }
}

object DiffUtil : DiffUtil.ItemCallback<Pair<LocData, Cell>>() {
    override fun areContentsTheSame(
        oldItem: Pair<LocData, Cell>,
        newItem: Pair<LocData, Cell>
    ): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(
        oldItem: Pair<LocData, Cell>,
        newItem: Pair<LocData, Cell>
    ): Boolean {
        return oldItem == newItem
    }
}