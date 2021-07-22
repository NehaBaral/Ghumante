package com.webtechsolution.ghumantey.ui.agency.agencyHome.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.webtechsolution.ghumantey.data.domain.AgencyPackageItem
import com.webtechsolution.ghumantey.data.domain.PackagesListItem
import com.webtechsolution.ghumantey.data.model.DestinationModel
import com.webtechsolution.ghumantey.databinding.MyBookingRvBinding
import com.webtechsolution.ghumantey.ui.myBooking.adapter.MyBookingAdapter
import dagger.hilt.android.scopes.FragmentScoped

@FragmentScoped
class AgencyHomeAdapter : ListAdapter<AgencyPackageItem,AgencyHomeAdapter.AgencyHomeViewHolder>(AgencyHomeDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AgencyHomeViewHolder {
        return AgencyHomeViewHolder(MyBookingRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: AgencyHomeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class AgencyHomeViewHolder(val binding:MyBookingRvBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AgencyPackageItem?) {
            binding.packageName.text = item?.name
            binding.phoneNum.text = item?.phone.toString()
            binding.packageAmount.text = "Rs ${item?.price.toString()}"
            if (item?.days.isNullOrEmpty()){
                binding.packageDays.text = "Not Mentioned"
            }else {
                binding.packageDays.text = "${item?.days} days"
            }
        }
    }
}
class AgencyHomeDiff : DiffUtil.ItemCallback<AgencyPackageItem>() {
    override fun areItemsTheSame(oldItem: AgencyPackageItem, newItem: AgencyPackageItem) = oldItem == newItem

    override fun areContentsTheSame(oldItem: AgencyPackageItem, newItem: AgencyPackageItem) = oldItem == newItem

    override fun getChangePayload(oldItem: AgencyPackageItem, newItem: AgencyPackageItem) = Unit
}