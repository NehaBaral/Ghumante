package com.webtechsolution.ghumantey.ui.myBooking.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.webtechsolution.ghumantey.data.domain.AgencyPackageItem
import com.webtechsolution.ghumantey.data.model.DestinationModel
import com.webtechsolution.ghumantey.databinding.MyBookingRvBinding
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@FragmentScoped
class MyBookingAdapter @Inject constructor() : ListAdapter<AgencyPackageItem,MyBookingAdapter.MyBookingViewHolder>(MyBookingDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyBookingViewHolder {
        return MyBookingViewHolder(MyBookingRvBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyBookingViewHolder, position: Int) {
      holder.bind(getItem(position))
    }

    class MyBookingViewHolder(val binding:MyBookingRvBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AgencyPackageItem?) {

        }
    }

}

class MyBookingDiff : DiffUtil.ItemCallback<AgencyPackageItem>() {
    override fun areItemsTheSame(oldItem: AgencyPackageItem, newItem: AgencyPackageItem) = oldItem == newItem

    override fun areContentsTheSame(oldItem: AgencyPackageItem, newItem: AgencyPackageItem) = oldItem == newItem

    override fun getChangePayload(oldItem: AgencyPackageItem, newItem: AgencyPackageItem) = Unit
}