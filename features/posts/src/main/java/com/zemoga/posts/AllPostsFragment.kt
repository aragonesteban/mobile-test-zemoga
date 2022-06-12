package com.zemoga.posts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.zemoga.domain.model.PostItem
import com.zemoga.posts.adapter.PostsAdapter
import com.zemoga.posts.databinding.FragmentAllPostsBinding
import com.zemoga.shared.extensions.toggleVisibility
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.core.qualifier.named

class AllPostsFragment : Fragment() {

    private val postsViewModel: PostsViewModel by sharedViewModel(named(POSTS_VIEW_MODEL))

    private var _binding: FragmentAllPostsBinding? = null
    private val binding get() = _binding!!

    private val postsAdapter = PostsAdapter()

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
        postsViewModel.getAllPosts()
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                postsViewModel.viewState.collect(::handleViewState)
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
        when (viewState) {
            PostsUiState.Loading -> binding.allPostsLoading.toggleVisibility(show = true)
            is PostsUiState.ShowAllPosts -> setDataPostsList(viewState.data)
            PostsUiState.ShowEmptyPosts -> showEmptyPosts()
            PostsUiState.Error -> showErrorFeedback()
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
        Toast.makeText(
            requireContext(),
            getString(com.zemoga.shared.R.string.label_error),
            Toast.LENGTH_LONG
        ).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.postsList.adapter = null
        _binding = null
    }

}