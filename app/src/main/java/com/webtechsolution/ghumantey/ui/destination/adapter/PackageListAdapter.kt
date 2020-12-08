package com.webtechsolution.ghumantey.ui.destination.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.webtechsolution.ghumantey.data.model.DestinationModel
import com.webtechsolution.ghumantey.databinding.PackageListRvBinding
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@FragmentScoped
class PackageListAdapter @Inject constructor() : ListAdapter<DestinationModel,PackageListAdapter.PackageListViewHolder>(PackageListDiff()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PackageListViewHolder {
        return PackageListViewHolder(PackageListRvBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: PackageListViewHolder, position: Int) {

    }

    class PackageListViewHolder(binding:PackageListRvBinding) : RecyclerView.ViewHolder(binding.root)

}

class PackageListDiff : DiffUtil.ItemCallback<DestinationModel>() {
    override fun areItemsTheSame(oldItem: DestinationModel, newItem: DestinationModel) = oldItem == newItem

    override fun areContentsTheSame(oldItem: DestinationModel, newItem: DestinationModel) = oldItem == newItem

    override fun getChangePayload(oldItem: DestinationModel, newItem: DestinationModel) = Unit
}