package com.uma.trailsmartapp.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.uma.trailsmartapp.R
import com.uma.trailsmartapp.databinding.BattingRecyclerviewBinding
import com.uma.trailsmartapp.models.Batsman

/**
 * Created by Umapathi on 11/03/19.
 * Copyright Indyzen Inc, @2019
 */
class BattingAdapter(var battingLIst: ArrayList<Batsman>) : RecyclerView.Adapter<BattingAdapter.MyViewHolder>() {

    lateinit var battingRecyclerviewBinding: com.uma.trailsmartapp.databinding.BattingRecyclerviewBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BattingAdapter.MyViewHolder {

        battingRecyclerviewBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.batting_recyclerview, parent, false)
        return MyViewHolder(battingRecyclerviewBinding)
    }

    override fun getItemCount(): Int {
        return battingLIst.size
    }

    override fun onBindViewHolder(holder: BattingAdapter.MyViewHolder, position: Int) {
        holder.onBind(battingLIst[position])

    }

    class MyViewHolder(var view: BattingRecyclerviewBinding) : RecyclerView.ViewHolder(view.root) {

        fun onBind(item: Batsman) {
            view.batting = item
        }
    }
}