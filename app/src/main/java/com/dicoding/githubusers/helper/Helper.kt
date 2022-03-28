package com.dicoding.githubusers.helper

import com.dicoding.githubusers.data.User
import org.json.JSONArray
import org.json.JSONObject

class Helper {
    companion object {
        fun listUserResponse(items: JSONArray): ArrayList<User> {
            val listUser = ArrayList<User>()
            for (i in 0 until items.length()) {
                val item = items.getJSONObject(i)
                val dataUser = User()
                dataUser.username = item.getString("login")
                dataUser.avatar = item.getString("avatar_url")
                listUser.add(dataUser)
            }
            return listUser
        }

        fun detailUserResponse(item: JSONObject): User {
            val dataUser = User()
            dataUser.avatar = item.getString("avatar_url")
            dataUser.username = item.getString("login")
            dataUser.name = item.getString("name")
            dataUser.loc = item.getString("location")
            dataUser.repo = item.getInt("public_repos")
            dataUser.followers = item.getInt("followers")
            dataUser.following = item.getInt("following")
            return dataUser
        }
    }
}