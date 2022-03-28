package com.dicoding.githubusers.api

import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.loopj.android.http.RequestHandle

class UserApi {
    companion object {

        private const val URL = "https://api.github.com"

        private fun getClient(): AsyncHttpClient {
            val client = AsyncHttpClient()
            client.addHeader("Authorization", "token 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")
            client.addHeader("User-Agent", "request")
            return client
        }

        fun getSearch(username: String, responseHandler: AsyncHttpResponseHandler): RequestHandle? {
            val url = "$URL/search/users?q=$username"
            return getClient().get(url, responseHandler)
        }

        fun getDetailUser(username: String, responseHandler: AsyncHttpResponseHandler): RequestHandle? {
            val url = "$URL/users/$username"
            return getClient().get(url, responseHandler)
        }

        fun getFollowers(username: String, responseHandler: AsyncHttpResponseHandler): RequestHandle? {
            val url = "$URL/users/$username/followers"
            return getClient().get(url, responseHandler)
        }

        fun getFollowing(username: String, responseHandler: AsyncHttpResponseHandler): RequestHandle? {
            val url = "$URL/users/$username/following"
            return getClient().get(url, responseHandler)
        }
    }
}