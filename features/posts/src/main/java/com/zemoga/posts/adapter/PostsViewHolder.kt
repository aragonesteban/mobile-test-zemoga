package com.zemoga.posts.adapter

import androidx.recyclerview.widget.RecyclerView
import com.zemoga.domain.model.PostItem
import com.zemoga.posts.databinding.ItemPostBinding
import com.zemoga.shared.extensions.toggleVisibility

class PostsViewHolder(
    private val binding: ItemPostBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(postItem: PostItem, onPostItemClick: (PostItem) -> Unit) {
        binding.apply {
            postTitle.text = postItem.title
            postFavorite.toggleVisibility(show = postItem.isFavorite)
            root.setOnClickListener { onPostItemClick.invoke(postItem) }
        }
    }

}