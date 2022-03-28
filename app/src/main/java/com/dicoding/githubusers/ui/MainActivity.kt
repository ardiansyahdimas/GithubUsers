package com.dicoding.githubusers.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubusers.R
import com.dicoding.githubusers.adapter.ListUserAdapter
import com.dicoding.githubusers.data.User
import com.dicoding.githubusers.databinding.ActivityMainBinding
import com.dicoding.githubusers.ui.viewmodel.UserViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var userAdapter: ListUserAdapter
    private lateinit var userViewModel: UserViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(UserViewModel::class.java)
        if(applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            binding.rvUsers.layoutManager = GridLayoutManager(this, 2)
        } else  {
            binding.rvUsers.layoutManager = LinearLayoutManager(this)
        }

        userAdapter = ListUserAdapter()
        binding.rvUsers.adapter = userAdapter
        userAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: User) {
                val moveIntent = Intent(this@MainActivity, DetailUser::class.java)
                moveIntent.putExtra(DetailUser.EXTRA_USER, data.username)
                startActivity(moveIntent)
            }
        })

        userViewModel.setSearchUsers(resources)
        progressBarDisplay(true)
        userViewModel.listUser.observe(this, {userItem ->
            if (userItem != null) {
                userAdapter.listUser = userItem
                progressBarDisplay(false)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                userViewModel.setSearchUsers(resources, query)
                progressBarDisplay(true)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()) {
                    progressBarDisplay(false)
                } else {
                    userViewModel.setSearchUsers(resources, newText)
                    progressBarDisplay(true)
                }
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.settings){
            val myIntent = Intent(this@MainActivity, SettingActivity::class.java)
            startActivity(myIntent)
        }
        if(item.itemId == R.id.favorite){
            val myIntent = Intent(this@MainActivity, FavoriteUser::class.java)
            startActivity(myIntent)
        }
        return super.onOptionsItemSelected(item)
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

}