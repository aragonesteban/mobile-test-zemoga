package com.zemoga.posts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.tabs.TabLayoutMediator
import com.zemoga.posts.databinding.ActivityPostsBinding
import com.zemoga.shared.ViewPagerAdapter

private const val ALL_POSTS_TAB_INDEX = 0
private const val FAVORITES_POSTS_TAB_INDEX = 1

class PostsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPostsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpTabs()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_posts, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_reload -> {
                true
            }
            else -> super.onOptionsItemSelected(item)
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


}