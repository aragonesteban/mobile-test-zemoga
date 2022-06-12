package com.zemoga.posts

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayoutMediator
import com.zemoga.posts.databinding.ActivityPostsBinding
import com.zemoga.shared.ViewPagerAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named
import com.zemoga.shared.R.style

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
                postsViewModel.getAllPosts()
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
        builder.setPositiveButton(R.string.btn_accept) { _, _ -> deleteAllPosts() }
        builder.setNegativeButton(R.string.btn_cancel, null)

        builder.create().show()
    }

    private fun deleteAllPosts() {
        postsViewModel.deleteAllPosts()
        Toast.makeText(
            this,
            getString(R.string.label_all_posts_deleted),
            Toast.LENGTH_LONG
        ).show()
    }

}