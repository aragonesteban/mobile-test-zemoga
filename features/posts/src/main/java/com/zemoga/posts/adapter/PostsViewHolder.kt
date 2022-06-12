package com.zemoga.posts.adapter

import androidx.recyclerview.widget.RecyclerView
import com.zemoga.domain.model.PostItem
import com.zemoga.posts.databinding.ItemPostBinding

class PostsViewHolder(
    private val binding: ItemPostBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(postItem: PostItem) {
        binding.apply {
            postTitle.text = postItem.title
        }
    }

}