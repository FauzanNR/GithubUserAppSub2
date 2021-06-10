package com.app.githubuserappsub2.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [GitUserDao::class], version = 1, exportSchema = false)
abstract class GitHubUserRoomDB : RoomDatabase() {

    abstract fun userDao(): Dao

    companion object {
        @Volatile
        private var INSTANCE: GitHubUserRoomDB? = null

        fun getDb(context: Context): GitHubUserRoomDB {
            if (INSTANCE != null) {
                return INSTANCE as GitHubUserRoomDB
            }
            synchronized(this) {
                val instace = Room.databaseBuilder(
                    context.applicationContext,
                    GitHubUserRoomDB::class.java,
                    "fav_user_db"
                ).build()

                INSTANCE = instace
                return instace
            }
        }
    }
}