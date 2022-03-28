package com.dicoding.githubusers.ui

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubusers.adapter.ListUserAdapter
import com.dicoding.githubusers.data.User
import com.dicoding.githubusers.databinding.ActivityFavoriteUserBinding
import com.dicoding.githubusers.ui.viewmodel.UserViewModel

class FavoriteUser : AppCompatActivity() {
    private lateinit var favoriteAdapter: ListUserAdapter
    private lateinit var binding: ActivityFavoriteUserBinding
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionbar = supportActionBar
        actionbar!!.title = "Favorite User"
        actionbar.setDisplayHomeAsUpEnabled(true)

        userViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(UserViewModel::class.java)
        if(applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            binding.rvUsers.layoutManager = GridLayoutManager(this, 2)
        } else  {
            binding.rvUsers.layoutManager = LinearLayoutManager(this)
        }

        favoriteAdapter = ListUserAdapter()
        binding.rvUsers.adapter = favoriteAdapter
        favoriteAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: User) {
                val moveIntent = Intent(this@FavoriteUser, DetailUser::class.java)
                moveIntent.putExtra(DetailUser.EXTRA_USER, data.username)
                startActivity(moveIntent)
            }
        })

        userViewModel.setSearchUsers(resources)
        progressBarDisplay(true)
        userViewModel.favorite.observe(this, { userItem->
            if (userItem != null) {
                favoriteAdapter.listUser = ArrayList(userItem)
                progressBarDisplay(false)
            }
        })
    }

    private fun progressBarDisplay(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
            binding.rvUsers.visibility = View.GONE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.rvUsers.visibility = View.VISIBLE
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}