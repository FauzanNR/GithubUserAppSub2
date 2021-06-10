package com.app.githubuserappsub2.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.app.githubuserappsub2.roomdb.DataBaseRepository
import com.app.githubuserappsub2.roomdb.GitHubUserRoomDB
import com.app.githubuserappsub2.roomdb.GitUserDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DataBaseAndroidViewModel(application: Application) : AndroidViewModel(application) {
    val selectData: LiveData<List<GitUserDao>>

    private val repository: DataBaseRepository

    init {
        val dao = GitHubUserRoomDB.getDb(application).userDao()
        repository = DataBaseRepository(data = dao)
        selectData = repository.readAll
    }

    fun addData(dataUser: GitUserDao) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addData(dataUser)
        }
    }


    fun searchData(user: String): LiveData<List<GitUserDao>> {
        return repository.searchData(user)
    }

    fun deleteData(user: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteData(user)
        }
    }
}