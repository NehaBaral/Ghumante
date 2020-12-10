package com.webtechsolution.ghumantey.ui.destination.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding3.view.clicks
import com.webtechsolution.ghumantey.data.model.DestinationModel
import com.webtechsolution.ghumantey.databinding.PackageListRvBinding
import dagger.hilt.android.scopes.FragmentScoped
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

@FragmentScoped
class PackageListAdapter @Inject constructor() : ListAdapter<DestinationModel,PackageListAdapter.PackageListViewHolder>(PackageListDiff()){
    private val clickRelay: PublishSubject<DestinationModel> = PublishSubject.create()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PackageListViewHolder {
        return PackageListViewHolder(clickRelay,PackageListRvBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    fun clicks() = clickRelay
    override fun onBindViewHolder(holder: PackageListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class PackageListViewHolder(private val clickRelay: PublishSubject<DestinationModel>,val binding:PackageListRvBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DestinationModel) {
            binding.root.clicks().map { item }.subscribe(clickRelay)
        }
    }

}

class PackageListDiff : DiffUtil.ItemCallback<DestinationModel>() {
    override fun areItemsTheSame(oldItem: DestinationModel, newItem: DestinationModel) = oldItem == newItem

    override fun areContentsTheSame(oldItem: DestinationModel, newItem: DestinationModel) = oldItem == newItem

    override fun getChangePayload(oldItem: DestinationModel, newItem: DestinationModel) = Unit
}