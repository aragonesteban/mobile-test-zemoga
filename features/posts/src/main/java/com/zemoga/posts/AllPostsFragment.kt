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
import com.zemoga.posts.databinding.FragmentAllPostsBinding
import com.zemoga.shared.R
import com.zemoga.shared.extensions.showToastMessage
import com.zemoga.shared.extensions.toggleVisibility
import com.zemoga.shared.navigation.goToPostDetail
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.core.qualifier.named

class AllPostsFragment : Fragment() {

    private val postsViewModel: PostsViewModel by sharedViewModel(named(POSTS_VIEW_MODEL))

    private var _binding: FragmentAllPostsBinding? = null
    private val binding get() = _binding!!

    private val postsAdapter = PostsAdapter { postItem ->
        startActivity(Intent().goToPostDetail(requireContext(), postItem.id, postItem.userId))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllPostsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecycler()
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                postsViewModel.uiStateAllPosts.collect(::handleViewState)
            }
        }
    }

    private fun setUpRecycler() {
        binding.postsList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = postsAdapter
        }
    }

    private fun handleViewState(viewState: PostsUiState) {
        binding.apply {
            when (viewState) {
                PostsUiState.Loading -> {
                    labelEmptyPosts.toggleVisibility(show = false)
                    allPostsLoading.toggleVisibility(show = true)
                }
                is PostsUiState.ShowAllPosts -> setDataPostsList(viewState.data)
                PostsUiState.ShowEmptyPosts -> showEmptyPosts()
                PostsUiState.Error -> showErrorFeedback()
            }
        }
    }

    private fun setDataPostsList(value: List<PostItem>) {
        binding.apply {
            allPostsLoading.toggleVisibility(show = false)
            labelEmptyPosts.toggleVisibility(show = false)
            postsList.toggleVisibility(show = true)
        }
        postsAdapter.setPostsList(value)
    }

    private fun showEmptyPosts() {
        postsAdapter.setPostsList(listOf())
        binding.apply {
            postsList.toggleVisibility(show = false)
            labelEmptyPosts.toggleVisibility(show = true)
        }
    }

    private fun showErrorFeedback() {
        requireContext().showToastMessage(getString(R.string.label_error))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.postsList.adapter = null
        _binding = null
    }

}