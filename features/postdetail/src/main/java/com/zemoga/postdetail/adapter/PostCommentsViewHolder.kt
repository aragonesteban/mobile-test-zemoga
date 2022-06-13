package com.zemoga.postdetail.adapter

import androidx.recyclerview.widget.RecyclerView
import com.zemoga.domain.model.CommentItem
import com.zemoga.postdetail.databinding.ItemPostCommentBinding

class PostCommentsViewHolder(
    private val binding: ItemPostCommentBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(comment: CommentItem) {
        binding.apply {
            commentTitle.text = comment.name
            commentBody.text = comment.body
        }
    }

}