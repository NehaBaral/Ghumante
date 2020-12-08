package com.webtechsolution.ghumantey.ui.packageDetail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.webtechsolution.ghumantey.data.model.DestinationModel
import com.webtechsolution.ghumantey.databinding.IternaryRvBinding
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@FragmentScoped
class IternaryAdapter @Inject constructor() : ListAdapter<DestinationModel,IternaryAdapter.IternaryViewHolder>(IternaryDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IternaryViewHolder {
        return IternaryViewHolder(IternaryRvBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: IternaryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class IternaryViewHolder(binding:IternaryRvBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DestinationModel) {

        }
    }
}
class IternaryDiff : DiffUtil.ItemCallback<DestinationModel>() {
    override fun areItemsTheSame(oldItem: DestinationModel, newItem: DestinationModel) = oldItem == newItem

    override fun areContentsTheSame(oldItem: DestinationModel, newItem: DestinationModel) = oldItem == newItem

}