package com.app.githubuserappsub2.roomdb

import androidx.lifecycle.LiveData

class DataBaseRepository(private val data: Dao) {

    val readAll: LiveData<List<GitUserDao>> = data.select()

    fun searchData(key: String): LiveData<List<GitUserDao>> {
        return data.search(key)
    }

    suspend fun addData(dataUser: GitUserDao) {
        data.insert(dataUser)
    }

    suspend fun deleteData(key: String) {
        data.delete(key)
    }
}