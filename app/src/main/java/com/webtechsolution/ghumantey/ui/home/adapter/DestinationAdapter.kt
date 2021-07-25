package com.webtechsolution.ghumantey.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jakewharton.rxbinding3.view.clicks
import com.webtechsolution.ghumantey.R
import com.webtechsolution.ghumantey.data.domain.AgencyPackageItem
import com.webtechsolution.ghumantey.databinding.DestinationRvBinding
import dagger.hilt.android.scopes.FragmentScoped
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

@FragmentScoped
class DestinationAdapter @Inject constructor() : ListAdapter<AgencyPackageItem, DestinationAdapter.DestinationViewHolder>(DestinationDiff()) {
    private val clickRelay: PublishSubject<AgencyPackageItem> = PublishSubject.create()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DestinationViewHolder {
        return DestinationViewHolder(clickRelay,DestinationRvBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }
    fun clicks() = clickRelay
    override fun onBindViewHolder(holder: DestinationViewHolder, position: Int) {
       holder.bind(getItem(position))
    }

    class DestinationViewHolder(private val clickRelay: PublishSubject<AgencyPackageItem>,val binding:DestinationRvBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AgencyPackageItem) {
            binding.destinationName.text = item.destination
            Glide.with(binding.root.context)
                .load(item.image)
                .placeholder(R.drawable.testimage)
                .into(binding.destinationImage)
            binding.root.clicks().map { item }.subscribe(clickRelay)
        }
    }
}

class DestinationDiff : DiffUtil.ItemCallback<AgencyPackageItem>() {
    override fun areItemsTheSame(oldItem: AgencyPackageItem, newItem: AgencyPackageItem) = oldItem == newItem

    override fun areContentsTheSame(oldItem: AgencyPackageItem, newItem: AgencyPackageItem) = oldItem == newItem

    override fun getChangePayload(oldItem: AgencyPackageItem, newItem: AgencyPackageItem) = Unit
}