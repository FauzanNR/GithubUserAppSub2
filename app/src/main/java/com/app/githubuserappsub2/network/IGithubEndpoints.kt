package com.app.githubuserappsub2.network

import com.app.githubuserappsub2.datamodel.GithubUser
import com.app.githubuserappsub2.datamodel.Search
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface IGithubEndpoints {

    @GET("users/{username}")
    @Headers("Authorization: token ${ApiServices.tokenAPI}")
    suspend fun getDetailUser(
        @Path("username")
        username: String? = ""
    ): Response<GithubUser>
//https://api.github.com/search/users?q={username}
    @GET("search/users")
    @Headers("Authorization: token ${ApiServices.tokenAPI}")
    suspend fun getSearchUser(
        @Query("q")
        q: String? = " "
    ): Response<Search>

    @GET("users/{username}/{follow}")
    @Headers("Authorization: token ${ApiServices.tokenAPI}")
    suspend fun getFollow(
        @Path("username") username: String? = "",
        @Path("follow") follow: String? = ""
    ): Response<List<GithubUser>>

//    @GET("users/{username}/repos")
//    @Headers("Authorization: token ${ApiServices.tokenAPI}")
//    suspend fun getRepos(
//        @Path("username") username: String? = ""
//    ): Response<List<Repo>>
}