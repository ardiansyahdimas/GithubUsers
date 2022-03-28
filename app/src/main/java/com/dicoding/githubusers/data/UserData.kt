package com.dicoding.githubusers.data

import android.content.res.Resources
import android.content.res.TypedArray
import com.dicoding.githubusers.R

class UserData {
    companion object {
        private var listUsers = arrayListOf<User>()

        private lateinit var dAvatar: TypedArray
        private lateinit var dUsername: Array<String>
        private lateinit var dName: Array<String>
        private lateinit var dLoc: Array<String>
        private lateinit var dRepo: IntArray
        private lateinit var dFollowers: IntArray
        private lateinit var dFollowing: IntArray

        private fun userResource(resources: Resources) {
            dAvatar = resources.obtainTypedArray(R.array.data_avatar)
            dUsername = resources.getStringArray(R.array.data_username)
            dName = resources.getStringArray(R.array.data_name)
            dLoc = resources.getStringArray(R.array.data_location)
            dRepo = resources.getIntArray(R.array.data_repo)
            dFollowers = resources.getIntArray(R.array.data_followers)
            dFollowing = resources.getIntArray(R.array.data_following)
        }

        private fun selectedItem() {
            for (position in dName.indices) {
                val dataUser = User(
                    "",
                    dUsername[position],
                    dName[position],
                    dLoc[position],
                    dRepo[position],
                    dFollowers[position],
                    dFollowing[position],
                    dAvatar.getResourceId(position, -1),
                )
                listUsers.add(dataUser)
            }
        }

        fun getListUsers(resources: Resources): ArrayList<User> {
            userResource(resources)
            selectedItem()
            return listUsers
        }
    }
}