package com.webtechsolution.ghumantey.ui.myBooking.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.webtechsolution.ghumantey.data.domain.AgencyPackageItem
import com.webtechsolution.ghumantey.data.domain.BookingPackageItem
import com.webtechsolution.ghumantey.databinding.MyBookingRvBinding
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@FragmentScoped
class MyBookingAdapter @Inject constructor() : ListAdapter<BookingPackageItem,MyBookingAdapter.MyBookingViewHolder>(MyBookingDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyBookingViewHolder {
        return MyBookingViewHolder(MyBookingRvBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyBookingViewHolder, position: Int) {
      holder.bind(getItem(position))
    }

    class MyBookingViewHolder(val binding:MyBookingRvBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: BookingPackageItem) {
        }
    }

}

class MyBookingDiff : DiffUtil.ItemCallback<BookingPackageItem>() {
    override fun areItemsTheSame(oldItem: BookingPackageItem, newItem: BookingPackageItem) = oldItem == newItem

    override fun areContentsTheSame(oldItem: BookingPackageItem, newItem: BookingPackageItem) = oldItem == newItem

    override fun getChangePayload(oldItem: BookingPackageItem, newItem: BookingPackageItem) = Unit
}