package com.dicoding.githubusers.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.githubusers.R
import com.dicoding.githubusers.adapter.SectionsPagerAdapter
import com.dicoding.githubusers.data.User
import com.dicoding.githubusers.databinding.ActivityDetailUserBinding
import com.dicoding.githubusers.ui.viewmodel.UserViewModel
import com.google.android.material.tabs.TabLayoutMediator

class DetailUser : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var user: User
    private lateinit var userViewModel: UserViewModel

    companion object {
        const val EXTRA_USER = "extra_user"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionbar = supportActionBar
        actionbar!!.title = "Detail User"
        actionbar.setDisplayHomeAsUpEnabled(true)

        userViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(UserViewModel::class.java)
        val username = intent.getStringExtra(EXTRA_USER) as String

        userViewModel.setDetailUser(username)
        userViewModel.user.observe(this, {dataUser ->
            if(dataUser != null) {
                Glide.with(this)
                    .load(dataUser.avatar)
                    .apply(RequestOptions())
                    .circleCrop()
                    .into(binding.imgItemPhoto)
                binding.tvItemName.text = dataUser.name
                binding.tvUsername.text = dataUser.username
                binding.tvLocation.text = dataUser.loc
                binding.tvRepositories.text = "Repositories: ${dataUser.repo}"
                user = dataUser
                userViewModel.updateFavorite(user)
            }
        })
        userViewModel.isFavorite.observe(this, {
            invalidateOptionsMenu()
        })

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = intent.getStringExtra(EXTRA_USER) as String
        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.fav_menu, menu)
        if (userViewModel.isFavorite.value == true) menu?.getItem(0)?.setIcon(R.drawable.ic_baseline_favorite_24)
        else menu?.getItem(0)?.setIcon(R.drawable.ic_baseline_favorite_border_24)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.favorite) {
            if (userViewModel.isFavorite.value == true) {
                userViewModel.deleteFavorite(user)
            } else {
                userViewModel.insertFavorite(user)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}