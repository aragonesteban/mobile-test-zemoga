package com.zemoga.posts

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.zemoga.domain.model.PostItem
import com.zemoga.posts.adapter.PostsAdapter
import com.zemoga.posts.databinding.FragmentFavoritesPostsBinding
import com.zemoga.shared.R
import com.zemoga.shared.extensions.showToastMessage
import com.zemoga.shared.extensions.toggleVisibility
import com.zemoga.shared.navigation.goToPostDetail
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.core.qualifier.named

class FavoritesPostsFragment : Fragment() {

    private val postsViewModel: PostsViewModel by sharedViewModel(named(POSTS_VIEW_MODEL))

    private var _binding: FragmentFavoritesPostsBinding? = null
    private val binding get() = _binding!!

    private val favoritesPostsAdapter = PostsAdapter { postItem ->
        startActivity(Intent().goToPostDetail(requireContext(), postItem.id, postItem.userId))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesPostsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecycler()
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                postsViewModel.uiFavoritePosts.collect(::handleViewState)
            }
        }
    }

    private fun setUpRecycler() {
        binding.favoritesPostsList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = favoritesPostsAdapter
        }
    }

    private fun handleViewState(viewState: FavoritesPostsUiState) {
        binding.apply {
            when (viewState) {
                FavoritesPostsUiState.Loading -> favoritesPostsLoading.toggleVisibility(show = true)
                is FavoritesPostsUiState.ShowFavoritesPosts -> setDataFavoritesPostsList(viewState.data)
                FavoritesPostsUiState.ShowEmptyFavoritesPosts -> showEmptyFavoritesPosts()
                FavoritesPostsUiState.Error -> showErrorFeedback()
            }
        }
    }

    private fun setDataFavoritesPostsList(value: List<PostItem>) {
        binding.apply {
            favoritesPostsLoading.toggleVisibility(show = false)
            labelEmptyFavoritesPosts.toggleVisibility(show = false)
            favoritesPostsList.toggleVisibility(show = true)
        }
        favoritesPostsAdapter.setPostsList(value)
    }

    private fun showEmptyFavoritesPosts() {
        binding.apply {
            favoritesPostsLoading.toggleVisibility(show = false)
            favoritesPostsList.toggleVisibility(show = false)
            labelEmptyFavoritesPosts.toggleVisibility(show = true)
        }
    }

    private fun showErrorFeedback() {
        requireContext().showToastMessage(getString(R.string.label_error))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.favoritesPostsList.adapter = null
        _binding = null
    }

}