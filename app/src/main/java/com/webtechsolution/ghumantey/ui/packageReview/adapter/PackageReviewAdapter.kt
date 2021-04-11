package com.webtechsolution.ghumantey.ui.packageReview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.webtechsolution.ghumantey.data.domain.CommentItem
import com.webtechsolution.ghumantey.databinding.PackageReviewItemBinding
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@FragmentScoped
class PackageReviewAdapter @Inject constructor() : ListAdapter<CommentItem, PackageReviewAdapter.PackageReviewViewHolder>(
    PackageReviewDiff()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PackageReviewViewHolder {
        return PackageReviewViewHolder(PackageReviewItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: PackageReviewViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class PackageReviewViewHolder(val binding: PackageReviewItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CommentItem) {
            binding.arthurName.text = item.author
            binding.reviewComment.text = item.comment
            binding.packageRating.rating = item.rating.toFloat()
        }
    }

}
class PackageReviewDiff : DiffUtil.ItemCallback<CommentItem>() {
    override fun areItemsTheSame(oldItem: CommentItem, newItem: CommentItem) = oldItem == newItem

    override fun areContentsTheSame(oldItem: CommentItem, newItem: CommentItem) = oldItem == newItem

    override fun getChangePayload(oldItem: CommentItem, newItem: CommentItem) = Unit
}
