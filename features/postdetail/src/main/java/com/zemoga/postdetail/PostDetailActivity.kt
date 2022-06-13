package com.zemoga.postdetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.zemoga.domain.DEFAULT_INT
import com.zemoga.domain.model.CommentItem
import com.zemoga.domain.model.PostItem
import com.zemoga.domain.model.User
import com.zemoga.postdetail.adapter.PostCommentsAdapter
import com.zemoga.postdetail.databinding.ActivityPostDetailBinding
import com.zemoga.shared.POST_ID_KEY
import com.zemoga.shared.R.drawable
import com.zemoga.shared.USER_ID_KEY
import com.zemoga.shared.extensions.showMessage
import com.zemoga.shared.extensions.showToastMessage
import com.zemoga.shared.extensions.toggleVisibility
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class PostDetailActivity : AppCompatActivity() {

    private val postDetailViewModel: PostDetailViewModel by viewModel(named(POST_DETAIL_VIEW_MODEL))

    private lateinit var binding: ActivityPostDetailBinding

    private val postCommentsAdapter = PostCommentsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpToolbar()
        setUpRecycler()
        handleClickListeners()

        intent?.apply {
            postDetailViewModel.getPostById(getIntExtra(POST_ID_KEY, DEFAULT_INT))
            postDetailViewModel.getUserById(getIntExtra(USER_ID_KEY, DEFAULT_INT))
            postDetailViewModel.getCommentsByPostId(getIntExtra(POST_ID_KEY, DEFAULT_INT))
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                postDetailViewModel.viewState.collect(::handleViewState)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setUpToolbar() {
        setSupportActionBar(binding.postDetailToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setUpRecycler() {
        binding.postDetailCommentsList.apply {
            layoutManager = LinearLayoutManager(this@PostDetailActivity)
            adapter = postCommentsAdapter
        }
    }

    private fun handleClickListeners() {
        binding.apply {
            postDetailFavorite.setOnClickListener {
                postDetailViewModel.updatePostFavorite()
            }
            postDetailDelete.setOnClickListener {
                showDialogToDeletePost()
            }
        }
    }

    private fun handleViewState(viewState: PostDetailUiState) {
        binding.apply {
            when (viewState) {
                PostDetailUiState.LoadingPostDetail -> postDetailLoading.toggleVisibility(show = true)
                PostDetailUiState.LoadingUserInfo -> postDetailUserLoading.toggleVisibility(show = true)
                PostDetailUiState.LoadingComments -> postDetailCommentsLoading.toggleVisibility(show = true)
                is PostDetailUiState.ShowPostDetail -> setDataPostDetail(viewState.data)
                is PostDetailUiState.ShowUserInfo -> setDataUserInfo(viewState.data)
                is PostDetailUiState.ShowPostComments -> setDataCommentsList(viewState.data)
                is PostDetailUiState.ToggleFavoriteStar -> toggleFavoriteStar(viewState.isFavorite)
                PostDetailUiState.ShowMessagePostDeleted -> showMessagePostDeleted()
                PostDetailUiState.Error -> showErrorFeedback()
            }
        }
    }

    private fun setDataPostDetail(postItem: PostItem) {
        binding.apply {
            postDetailLoading.toggleVisibility(show = false)
            postDetailTitle.text = postItem.title
            postDetailDescription.text = postItem.body
            toggleFavoriteStar(postItem.isFavorite, showMessage = false)
        }
    }

    private fun setDataUserInfo(user: User) {
        binding.apply {
            postDetailUserLoading.toggleVisibility(show = false)
            postDetailUserName.text = user.name
            postDetailUserEmail.text = user.email
            postDetailUserPhone.text = user.phone
            postDetailUserWebsite.text = user.website
        }
    }

    private fun setDataCommentsList(value: List<CommentItem>) {
        binding.postDetailCommentsLoading.toggleVisibility(show = false)
        postCommentsAdapter.setPostCommentsList(value)
    }

    private fun toggleFavoriteStar(isFavorite: Boolean, showMessage: Boolean = true) {
        val infoToggleFavorite = if (isFavorite) {
            Pair(drawable.ic_star, R.string.label_message_add_to_favorites)
        } else {
            Pair(drawable.ic_star_outline, R.string.label_message_delete_to_favorites)
        }
        if (showMessage) binding.root.showMessage(getString(infoToggleFavorite.second))
        binding.postDetailFavorite.setImageResource(infoToggleFavorite.first)
    }

    private fun showDialogToDeletePost() {
        val builder =
            MaterialAlertDialogBuilder(this, com.zemoga.shared.R.style.Theme_Zemoga_AlertDialog)

        builder.setTitle(getString(R.string.label_title_delete_post))
        builder.setMessage(getString(R.string.label_message_delete_post))
        builder.setPositiveButton(com.zemoga.shared.R.string.btn_accept) { _, _ -> postDetailViewModel.deletePost() }
        builder.setNegativeButton(com.zemoga.shared.R.string.btn_cancel, null)

        builder.create().show()
    }

    private fun showMessagePostDeleted() {
        showToastMessage(getString(R.string.label_message_post_deleted))
        finish()
    }

    private fun showErrorFeedback() {
        showToastMessage(getString(com.zemoga.shared.R.string.label_error))
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::binding.isInitialized) {
            binding.postDetailCommentsList.adapter = null
        }
    }

}