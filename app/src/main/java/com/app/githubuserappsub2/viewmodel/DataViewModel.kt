package com.app.githubuserappsub2.viewmodel

import androidx.lifecycle.*
import com.app.githubuserappsub2.datamodel.GithubUser
import com.app.githubuserappsub2.datamodel.Search
import com.app.githubuserappsub2.network.ApiServices
import com.app.githubuserappsub2.network.IGithubEndpoints
import kotlinx.coroutines.launch
import retrofit2.Response

class DataViewModel : ViewModel() {

    private var responseSearch = MutableLiveData<Response<Search>>()

    private val retrofitService =
        ApiServices.getRetrofitInstance()
            .create(IGithubEndpoints::class.java)

    fun setResponseLiveDataUser(user: String): LiveData<Response<GithubUser>> {
        val dataUser: LiveData<Response<GithubUser>> by lazy {
            liveData {
                val response: Response<GithubUser> = retrofitService.getDetailUser(user)
                emit(response)
            }
        }
        return dataUser
    }

    fun setResponseLiveDataFollow(
        user: String,
        type: String
    ): LiveData<Response<List<GithubUser>>> {
        val dataUser: LiveData<Response<List<GithubUser>>> by lazy {
            liveData {
                val response: Response<List<GithubUser>> = retrofitService.getFollow(user, type)
                emit(response)
            }
        }
        return dataUser
    }

//    fun setResponseLiveDataRepository(user: String): LiveData<Response<List<Repo>>> {
//        val dataUser: LiveData<Response<List<Repo>>> by lazy {
//            liveData {
//                val response: Response<List<Repo>> = retrofitService.getRepos(user)
//                emit(response)
//            }
//        }
//        return dataUser
//    }

//    fun setResponseLiveDataSearch(user: String): LiveData<Response<Search>> {
//        Log.d("RESPONSE_LOG", user)
//        val responseSearch: LiveData<Response<Search>> by lazy {
//            liveData {
//                val response: Response<Search> = retrofitService.getSearchUser(user)
//                emit(response)
//            }
//        }
//        return responseSearch
//    }

    fun setResponseLiveDataSearch(user: String) {
        viewModelScope.launch {
            val response: Response<Search> = retrofitService.getSearchUser(user)
            responseSearch.postValue(response)
        }
    }

    fun getDataSearch(): MutableLiveData<Response<Search>> {
        return responseSearch
    }

}