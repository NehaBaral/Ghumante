package com.webtechsolution.ghumantey.ui.myBooking.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.webtechsolution.ghumantey.R
import com.webtechsolution.ghumantey.data.domain.AgencyPackageItem
import com.webtechsolution.ghumantey.data.domain.BookingPackageItem
import com.webtechsolution.ghumantey.data.domain.SearchPackageItem
import com.webtechsolution.ghumantey.databinding.MyBookingRvBinding
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@FragmentScoped
class MyBookingAdapter @Inject constructor() : ListAdapter<SearchPackageItem,MyBookingAdapter.MyBookingViewHolder>(MyBookingDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyBookingViewHolder {
        return MyBookingViewHolder(MyBookingRvBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyBookingViewHolder, position: Int) {
      holder.bind(getItem(position))
    }

    class MyBookingViewHolder(val binding:MyBookingRvBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SearchPackageItem) {
            binding.packageName.text = item.name
            binding.packageAmount.text = item.price.toString()
            binding.packageDays.text = "${item.days} days"
            binding.phoneNum.text = item.email
            Glide.with(binding.root.context)
                .load(item.image)
                .placeholder(R.drawable.testimage)
                .into(binding.packageImage)
        }
    }

}

class MyBookingDiff : DiffUtil.ItemCallback<SearchPackageItem>() {
    override fun areItemsTheSame(oldItem: SearchPackageItem, newItem: SearchPackageItem) = oldItem == newItem

    override fun areContentsTheSame(oldItem: SearchPackageItem, newItem: SearchPackageItem) = oldItem == newItem

    override fun getChangePayload(oldItem: SearchPackageItem, newItem: SearchPackageItem) = Unit
}