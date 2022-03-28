package com.dicoding.githubusers.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding.githubusers.ui.fragment.FollowersFragment
import com.dicoding.githubusers.ui.fragment.FollowingFragment

class SectionsPagerAdapter (activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }
    var username: String = ""
    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> FollowersFragment.newInstance(username)
            else -> FollowingFragment.newInstance(username)
        }
    }

}