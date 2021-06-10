package com.app.githubuserappsub2.datamodel

data class Search(
    val incomplete_results: Boolean,
    val items: List<GithubUser>,
    val total_count: Int
)