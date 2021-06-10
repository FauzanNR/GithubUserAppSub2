package com.app.githubuserappsub2.roomdb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fav_user_table")
data class GitUserDao(

    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val login: String?,
    val name: String?,
    val company: String?,
    val avatar_url: String?,
    val location: String?,
)
