package com.dicoding.githubusers.database

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dicoding.githubusers.data.User

@Dao
interface UserDao {
    @Query("select * from favorite_user" )
    fun select() : LiveData<List<User>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Query("SELECT * from favorite_user ORDER BY username ASC")
    fun selectCursor(): Cursor

    @Query("SELECT * from favorite_user WHERE username =:username ORDER BY username ASC")
    fun selectCursor(username: String? = null): Cursor

    @Query("DELETE from favorite_user WHERE username = :username")
    fun drop(username: String?)

    @Query("SELECT COUNT(*) from favorite_user WHERE username = :username")
    fun num(username: String?) : Int
}