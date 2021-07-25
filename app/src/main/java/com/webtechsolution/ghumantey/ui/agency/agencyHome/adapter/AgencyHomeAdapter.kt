package com.webtechsolution.ghumantey.ui.agency.agencyHome.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jakewharton.rxbinding3.view.clicks
import com.webtechsolution.ghumantey.R
import com.webtechsolution.ghumantey.data.domain.AgencyPackageItem
import com.webtechsolution.ghumantey.data.domain.PackagesListItem
import com.webtechsolution.ghumantey.data.domain.SearchPackageItem
import com.webtechsolution.ghumantey.data.model.DestinationModel
import com.webtechsolution.ghumantey.databinding.MyBookingRvBinding
import com.webtechsolution.ghumantey.ui.myBooking.adapter.MyBookingAdapter
import dagger.hilt.android.scopes.FragmentScoped
import io.reactivex.subjects.PublishSubject

@FragmentScoped
class AgencyHomeAdapter : ListAdapter<AgencyPackageItem,AgencyHomeAdapter.AgencyHomeViewHolder>(AgencyHomeDiff()) {
    private val clickRelay: PublishSubject<AdaptorAction> = PublishSubject.create()
    fun clicks() = clickRelay

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AgencyHomeViewHolder {
        return AgencyHomeViewHolder(clickRelay,MyBookingRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: AgencyHomeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class AgencyHomeViewHolder(private val actionSubject: PublishSubject<AdaptorAction>,val binding:MyBookingRvBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AgencyPackageItem) {
            binding.packageName.text = item?.name
            binding.phoneNum.text = "Booked by ${if (item?.bookings?.size == 0) 0 else item?.bookings?.size } user"
            binding.packageAmount.text = "Rs ${item?.price.toString()}"
            if (item?.days.isNullOrEmpty()){
                binding.packageDays.text = "Not Mentioned"
            }else {
                binding.packageDays.text = "${item?.days} days"
            }

            Glide.with(binding.root.context)
                .load(item.image)
                .placeholder(R.drawable.testimage)
                .into(binding.packageImage)

            binding.bookedUser.setOnClickListener {
                actionSubject.onNext(AdaptorAction.UserDetailClicked(item))
            }

        }
    }
}
class AgencyHomeDiff : DiffUtil.ItemCallback<AgencyPackageItem>() {
    override fun areItemsTheSame(oldItem: AgencyPackageItem, newItem: AgencyPackageItem) = oldItem == newItem

    override fun areContentsTheSame(oldItem: AgencyPackageItem, newItem: AgencyPackageItem) = oldItem == newItem

    override fun getChangePayload(oldItem: AgencyPackageItem, newItem: AgencyPackageItem) = Unit
}

sealed class AdaptorAction {
    class UserDetailClicked(val agencyPackage: AgencyPackageItem) : AdaptorAction()
    class OnRootOnCLicked(val agencyPackage: AgencyPackageItem) : AdaptorAction()
}