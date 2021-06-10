package com.app.githubuserappsub2.datamodel

import android.os.Parcel
import android.os.Parcelable


data class GithubUser(
    val following_url: String?,
    val followers_url: String?,
    val login: String?,
    val name: String?,
    val company: String?,
    val location: String?,
    val repos_url: String?,
    val avatar_url: String?,
    val public_repos: Int?,
    val followers: Int?,
    val following: Int?,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(following_url)
        parcel.writeString(followers_url)
        parcel.writeString(login)
        parcel.writeString(name)
        parcel.writeString(company)
        parcel.writeString(location)
        parcel.writeString(repos_url)
        parcel.writeString(avatar_url)
        parcel.writeValue(public_repos)
        parcel.writeValue(followers)
        parcel.writeValue(following)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GithubUser> {
        override fun createFromParcel(parcel: Parcel): GithubUser {
            return GithubUser(parcel)
        }

        override fun newArray(size: Int): Array<GithubUser?> {
            return arrayOfNulls(size)
        }
    }
}