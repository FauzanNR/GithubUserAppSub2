package com.app.githubuserappsub2.datamodel

data class Repo(
    val full_name: String,
    val id: Int,
    val name: String,
    val node_id: String,
    val `private`: Boolean
)