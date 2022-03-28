package com.dicoding.githubusers.ui.viewmodel

import android.content.res.Resources
import android.util.Log
import androidx.lifecycle.*
import com.dicoding.githubusers.api.UserApi
import com.dicoding.githubusers.data.User
import com.dicoding.githubusers.data.UserData
import com.dicoding.githubusers.database.UserDatabase
import com.dicoding.githubusers.helper.Helper
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject


class UserViewModel : ViewModel() {
    private var database = UserDatabase.getDatabase().userDao()

    private var _listUser = MutableLiveData<ArrayList<User>>()
    val listUser : LiveData<ArrayList<User>> = _listUser

    private var _user = MutableLiveData<User>()
    val user : LiveData<User> =_user

    private var _followers = MutableLiveData<ArrayList<User>>()
    val followers : LiveData<ArrayList<User>> =_followers

    private var _following = MutableLiveData<ArrayList<User>>()
    val following : LiveData<ArrayList<User>> =_following

    private var _isFavorite = MutableLiveData<Boolean>()
    val isFavorite : LiveData<Boolean> = _isFavorite

    private var _favorite = database.select()
    val favorite : LiveData<List<User>> = _favorite

    fun setSearchUsers(resources: Resources, username: String? = null) {
        if(username == null){
            _listUser.postValue(UserData.getListUsers(resources))
        } else {
            UserApi.getSearch(username, object: AsyncHttpResponseHandler(){
                override fun onSuccess(
                    statusCode: Int,
                    headers: Array<Header>,
                    responseBody: ByteArray
                ) {
                    val result = String(responseBody)
                    try {
                        val responseObject = JSONObject(result)
                        val items = responseObject.getJSONArray("items")
                        _listUser.postValue(Helper.listUserResponse(items))
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                override fun onFailure(
                    statusCode: Int, headers: Array<Header>,
                    responseBody: ByteArray,
                    error: Throwable
                ) {
                    Log.d("onFailure", error.message.toString())
                }
            })
        }
    }

    fun setDetailUser(username: String) {
        UserApi.getDetailUser(username, object: AsyncHttpResponseHandler(){
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
                val result = String(responseBody)
                try {
                    val responseObject = JSONObject(result)
                    _user.postValue(Helper.detailUserResponse(responseObject))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                Log.d("onFailure", error.message.toString())
            }
        })
    }

    fun setFollowers(username: String) {
        UserApi.getFollowers(username, object: AsyncHttpResponseHandler(){
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
                val result = String(responseBody)
                try {
                    val responseObject = JSONArray(result)

                    _followers.postValue(Helper.listUserResponse(responseObject))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                Log.d("onFailure", error.message.toString())
            }
        })
    }

    fun setFollowing(username: String) {
        UserApi.getFollowing(username, object: AsyncHttpResponseHandler(){
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
                val result = String(responseBody)
                try {
                    val responseObject = JSONArray(result)

                    _following.postValue(Helper.listUserResponse(responseObject))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                Log.d("onFailure", error.message.toString())
            }
        })
    }

    fun insertFavorite(favUser: User) = viewModelScope.launch(Dispatchers.IO) {
        database.insert(favUser)
        updateFavorite(favUser)
    }

    fun updateFavorite(favUser: User) = viewModelScope.launch(Dispatchers.IO) {
        val isFavorite = database.num(favUser.username) > 0
        _isFavorite.postValue(isFavorite)
    }

    fun deleteFavorite(favUser: User) = viewModelScope.launch(Dispatchers.IO) {
        database.drop(favUser.username)
        updateFavorite(favUser)
    }

}