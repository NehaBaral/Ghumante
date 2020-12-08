package com.webtechsolution.ghumantey.ui.myBooking.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.webtechsolution.ghumantey.data.model.DestinationModel
import com.webtechsolution.ghumantey.databinding.MyBookingRvBinding
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@FragmentScoped
class MyBookingAdapter @Inject constructor() : ListAdapter<DestinationModel,MyBookingAdapter.MyBookingViewHolder>(MyBookingDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyBookingViewHolder {
        return MyBookingViewHolder(MyBookingRvBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyBookingViewHolder, position: Int) {
      holder.bind(getItem(position))
    }

    class MyBookingViewHolder(val binding:MyBookingRvBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DestinationModel?) {

        }
    }

}

class MyBookingDiff : DiffUtil.ItemCallback<DestinationModel>() {
    override fun areItemsTheSame(oldItem: DestinationModel, newItem: DestinationModel) = oldItem == newItem

    override fun areContentsTheSame(oldItem: DestinationModel, newItem: DestinationModel) = oldItem == newItem

    override fun getChangePayload(oldItem: DestinationModel, newItem: DestinationModel) = Unit
}