package com.zemoga.postdetail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.zemoga.domain.model.CommentItem
import com.zemoga.postdetail.databinding.ItemPostCommentBinding

class PostCommentsAdapter :
    ListAdapter<CommentItem, PostCommentsViewHolder>(CommentItemDiffCallback()) {

    fun setPostCommentsList(value: List<CommentItem>) {
        submitList(value)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostCommentsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PostCommentsViewHolder(
            binding = ItemPostCommentBinding.inflate(layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PostCommentsViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

}

private class CommentItemDiffCallback : DiffUtil.ItemCallback<CommentItem>() {

    override fun areItemsTheSame(oldItem: CommentItem, newItem: CommentItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CommentItem, newItem: CommentItem): Boolean {
        return oldItem == newItem
    }

}