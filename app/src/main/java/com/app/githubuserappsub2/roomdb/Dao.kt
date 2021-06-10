package com.app.githubuserappsub2.roomdb

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(data: GitUserDao)

    @Query("SELECT * FROM fav_user_table ORDER BY id ASC")
    fun select(): LiveData<List<GitUserDao>>

    @Query("SELECT * FROM fav_user_table WHERE login LIKE :keyword  ORDER BY id ASC")
    fun search(keyword: String): LiveData<List<GitUserDao>>

    @Query("DELETE FROM fav_user_table WHERE login =:keyword")
    suspend fun delete(keyword: String)


    //cursor
    @Query("SELECT * FROM fav_user_table ORDER BY id ASC")
    fun selectCursor(): Cursor
}