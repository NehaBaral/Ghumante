package com.webtechsolution.ghumantey.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding3.view.clicks
import com.webtechsolution.ghumantey.data.domain.PackagesListItem
import com.webtechsolution.ghumantey.data.model.Data
import com.webtechsolution.ghumantey.data.model.DestinationModel
import com.webtechsolution.ghumantey.databinding.DestinationRvBinding
import dagger.hilt.android.scopes.FragmentScoped
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

@FragmentScoped
class DestinationAdapter @Inject constructor() : ListAdapter<PackagesListItem, DestinationAdapter.DestinationViewHolder>(DestinationDiff()) {
    private val clickRelay: PublishSubject<PackagesListItem> = PublishSubject.create()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DestinationViewHolder {
        return DestinationViewHolder(clickRelay,DestinationRvBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }
    fun clicks() = clickRelay
    override fun onBindViewHolder(holder: DestinationViewHolder, position: Int) {
       holder.bind(getItem(position))
    }

    class DestinationViewHolder(private val clickRelay: PublishSubject<PackagesListItem>,val binding:DestinationRvBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PackagesListItem) {
            binding.destinationName.text = item.name
            binding.destinationAddress.text = item.agency
            binding.root.clicks().map { item }.subscribe(clickRelay)
        }
    }
}

class DestinationDiff : DiffUtil.ItemCallback<PackagesListItem>() {
    override fun areItemsTheSame(oldItem: PackagesListItem, newItem: PackagesListItem) = oldItem == newItem

    override fun areContentsTheSame(oldItem: PackagesListItem, newItem: PackagesListItem) = oldItem == newItem

    override fun getChangePayload(oldItem: PackagesListItem, newItem: PackagesListItem) = Unit
}