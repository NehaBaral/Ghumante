package com.webtechsolution.ghumantey.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding3.view.clicks
import com.webtechsolution.ghumantey.data.model.Data
import com.webtechsolution.ghumantey.data.model.DestinationModel
import com.webtechsolution.ghumantey.databinding.DestinationRvBinding
import dagger.hilt.android.scopes.FragmentScoped
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

@FragmentScoped
class DestinationAdapter @Inject constructor() : ListAdapter<Data, DestinationAdapter.DestinationViewHolder>(DestinationDiff()) {
    private val clickRelay: PublishSubject<Data> = PublishSubject.create()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DestinationViewHolder {
        return DestinationViewHolder(clickRelay,DestinationRvBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }
    fun clicks() = clickRelay
    override fun onBindViewHolder(holder: DestinationViewHolder, position: Int) {
       holder.bind(getItem(position))
    }

    class DestinationViewHolder(private val clickRelay: PublishSubject<Data>,val binding:DestinationRvBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Data) {
            binding.root.clicks().map { item }.subscribe(clickRelay)
        }
    }
}

class DestinationDiff : DiffUtil.ItemCallback<Data>() {
    override fun areItemsTheSame(oldItem: Data, newItem: Data) = oldItem == newItem

    override fun areContentsTheSame(oldItem: Data, newItem: Data) = oldItem == newItem

    override fun getChangePayload(oldItem: Data, newItem: Data) = Unit
}