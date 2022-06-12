package com.zemoga.posts.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.zemoga.domain.model.PostItem
import com.zemoga.posts.databinding.ItemPostBinding

class PostsAdapter : ListAdapter<PostItem, PostsViewHolder>(PostItemDiffCallback()) {

    fun setPostsList(value: List<PostItem>) {
        submitList(value)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PostsViewHolder(binding = ItemPostBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}


private class PostItemDiffCallback : DiffUtil.ItemCallback<PostItem>() {

    override fun areItemsTheSame(oldItem: PostItem, newItem: PostItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PostItem, newItem: PostItem): Boolean {
        return oldItem == newItem
    }

}