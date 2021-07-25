package com.webtechsolution.ghumantey.ui.packages.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jakewharton.rxbinding3.view.clicks
import com.webtechsolution.ghumantey.R
import com.webtechsolution.ghumantey.data.domain.SearchPackageItem
import com.webtechsolution.ghumantey.databinding.PackageListRvBinding
import dagger.hilt.android.scopes.FragmentScoped
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

@FragmentScoped
class PackageListAdapter @Inject constructor() :
    ListAdapter<SearchPackageItem, PackageListAdapter.PackageListViewHolder>(PackageListDiff()) {
    private val clickRelay: PublishSubject<SearchPackageItem> = PublishSubject.create()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PackageListViewHolder {
        return PackageListViewHolder(
            clickRelay,
            PackageListRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    fun clicks() = clickRelay
    override fun onBindViewHolder(holder: PackageListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class PackageListViewHolder(
        private val clickRelay: PublishSubject<SearchPackageItem>,
        val binding: PackageListRvBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SearchPackageItem) {
            binding.packageName.text = item.name
            binding.packageAmount.text = "Rs ${item.price.toString()}"
            Glide.with(binding.root.context)
                .load(item.image)
                .placeholder(R.drawable.testimage)
                .into(binding.packageImage)
            //  binding.packageDays.text = item
            //binding.packageCompany.text = item.agency.firstname
            binding.root.clicks().map { item }.subscribe(clickRelay)
        }
    }
}

class PackageListDiff : DiffUtil.ItemCallback<SearchPackageItem>() {
    override fun areItemsTheSame(oldItem: SearchPackageItem, newItem: SearchPackageItem) =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: SearchPackageItem, newItem: SearchPackageItem) =
        oldItem == newItem

    override fun getChangePayload(oldItem: SearchPackageItem, newItem: SearchPackageItem) = Unit
}