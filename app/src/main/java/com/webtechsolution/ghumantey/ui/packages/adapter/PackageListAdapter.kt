package com.webtechsolution.ghumantey.ui.packages.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding3.view.clicks
import com.webtechsolution.ghumantey.data.model.DestinationModel
import com.webtechsolution.ghumantey.data.model.PackagesModelItem
import com.webtechsolution.ghumantey.databinding.PackageListRvBinding
import dagger.hilt.android.scopes.FragmentScoped
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

@FragmentScoped
class PackageListAdapter @Inject constructor() : ListAdapter<PackagesModelItem,PackageListAdapter.PackageListViewHolder>(PackageListDiff()){
    private val clickRelay: PublishSubject<PackagesModelItem> = PublishSubject.create()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PackageListViewHolder {
        return PackageListViewHolder(clickRelay,PackageListRvBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    fun clicks() = clickRelay
    override fun onBindViewHolder(holder: PackageListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class PackageListViewHolder(private val clickRelay: PublishSubject<PackagesModelItem>,val binding:PackageListRvBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PackagesModelItem) {
            binding.packageName.text = item.title
            binding.packageAmount.text = item.price.toString()
            binding.packageDays.text = item.duration
            binding.packageCompany.text = item.agency
            binding.root.clicks().map { item }.subscribe(clickRelay)
        }
    }
}
class PackageListDiff : DiffUtil.ItemCallback<PackagesModelItem>() {
    override fun areItemsTheSame(oldItem: PackagesModelItem, newItem: PackagesModelItem) = oldItem == newItem

    override fun areContentsTheSame(oldItem: PackagesModelItem, newItem: PackagesModelItem) = oldItem == newItem

    override fun getChangePayload(oldItem: PackagesModelItem, newItem: PackagesModelItem) = Unit
}