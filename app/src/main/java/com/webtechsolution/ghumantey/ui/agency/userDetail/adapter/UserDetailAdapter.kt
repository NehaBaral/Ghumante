package com.webtechsolution.ghumantey.ui.agency.userDetail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.webtechsolution.ghumantey.data.domain.BookingPackageItem
import com.webtechsolution.ghumantey.databinding.MyBookingRvBinding
import dagger.hilt.android.scopes.FragmentScoped

@FragmentScoped
class UserDetailAdapter : ListAdapter<BookingPackageItem, UserDetailAdapter.AgencyHomeViewHolder>(AgencyHomeDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AgencyHomeViewHolder {
        return AgencyHomeViewHolder(MyBookingRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: AgencyHomeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class AgencyHomeViewHolder(val binding: MyBookingRvBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: BookingPackageItem?) {
            binding.packageName.text = item?.author?.username
            binding.phoneNum.text = item?.contactInfo
            binding.packageDays.text = item?.departureDate
            binding.packageAmount.text = "${item?.peopleCount} Traveller"
            binding.bookedUser.visibility = View.GONE
        }
    }
}
class AgencyHomeDiff : DiffUtil.ItemCallback<BookingPackageItem>() {
    override fun areItemsTheSame(oldItem: BookingPackageItem, newItem: BookingPackageItem) = oldItem == newItem

    override fun areContentsTheSame(oldItem: BookingPackageItem, newItem: BookingPackageItem) = oldItem == newItem

    override fun getChangePayload(oldItem: BookingPackageItem, newItem: BookingPackageItem) = Unit
}