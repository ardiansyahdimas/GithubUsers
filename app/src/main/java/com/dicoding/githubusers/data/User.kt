package com.dicoding.githubusers.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dicoding.githubusers.data.User.Companion.TABLE_NAME
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = TABLE_NAME)
data class User(

    @ColumnInfo(name = "avatar")
    var avatar: String = "",

    @ColumnInfo(name = "username")
    var username: String = "",

    @ColumnInfo(name = "name")
    var name: String = "",

    @ColumnInfo(name = "location")
    var loc: String = "",

    @ColumnInfo(name = "repository")
    var repo: Int = 0,

    @ColumnInfo(name = "followers")
    var followers: Int = 0,

    @ColumnInfo(name = "following")
    var following: Int = 0,

    @ColumnInfo(name = "avatarOf")
    var avatarOf: Int = 0,

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
) : Parcelable {
    companion object {
        const val TABLE_NAME = "favorite_user"
    }
}