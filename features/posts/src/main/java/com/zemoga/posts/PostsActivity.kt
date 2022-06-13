package com.zemoga.posts

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayoutMediator
import com.zemoga.posts.databinding.ActivityPostsBinding
import com.zemoga.shared.R.style
import com.zemoga.shared.ViewPagerAdapter
import com.zemoga.shared.extensions.isOnline
import com.zemoga.shared.extensions.showMessage
import com.zemoga.shared.extensions.toggleVisibility
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

private const val ALL_POSTS_TAB_INDEX = 0
private const val FAVORITES_POSTS_TAB_INDEX = 1

class PostsActivity : AppCompatActivity() {

    private val postsViewModel: PostsViewModel by viewModel(named(POSTS_VIEW_MODEL))

    private lateinit var binding: ActivityPostsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpToolbar()
        handleClickListeners()
        setUpTabs()
        binding.postsDelete.toggleVisibility(show = isOnline())
    }

    override fun onResume() {
        super.onResume()
        if (isOnline()) {
            postsViewModel.getAllPosts()
        }
    }

    private fun setUpToolbar() {
        setSupportActionBar(binding.postsToolbar)
        binding.postsToolbar.title = resources.getString(R.string.label_posts)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_posts, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_reload -> {
                if (isOnline()) {
                    postsViewModel.getAllPosts(loadPostsFromApi = true)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun handleClickListeners() {
        binding.apply {
            postsDelete.setOnClickListener { showDialogToDeleteAllPosts() }
        }
    }

    private fun setUpTabs() {
        val fragmentList = listOf(
            AllPostsFragment(),
            FavoritesPostsFragment()
        )

        val sectionsAdapter = ViewPagerAdapter(fragmentList = fragmentList, activity = this)
        binding.apply {
            postsSections.adapter = sectionsAdapter
            TabLayoutMediator(
                postsTabs,
                postsSections
            ) { tab, position ->
                tab.text = when (position) {
                    ALL_POSTS_TAB_INDEX -> getString(R.string.label_all_posts)
                    FAVORITES_POSTS_TAB_INDEX -> getString(R.string.label_favorites_posts)
                    else -> String()
                }
            }.attach()
        }
    }

    private fun showDialogToDeleteAllPosts() {
        val builder = MaterialAlertDialogBuilder(this, style.Theme_Zemoga_AlertDialog)

        builder.setTitle(getString(R.string.label_delete_all_posts))
        builder.setMessage(getString(R.string.label_description_delete_all_posts))
        builder.setPositiveButton(com.zemoga.shared.R.string.btn_accept) { _, _ -> deleteAllPosts() }
        builder.setNegativeButton(com.zemoga.shared.R.string.btn_cancel, null)

        builder.create().show()
    }

    private fun deleteAllPosts() {
        postsViewModel.deleteAllPosts()
        binding.root.showMessage(getString(R.string.label_all_posts_deleted))
    }

}