package com.app.githubuserappsub2.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.util.Log
import com.app.githubuserappsub2.roomdb.DbUri.AUTHORITY
import com.app.githubuserappsub2.roomdb.DbUri.DbProperty.Companion.TABLE_NAME
import com.app.githubuserappsub2.roomdb.GitHubUserRoomDB

class FavDataProvider : ContentProvider() {


    companion object {

        private const val all = 1
        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            sUriMatcher.addURI(AUTHORITY, TABLE_NAME, all)
        }
    }

    override fun onCreate(): Boolean {

        Log.d("Provider", sUriMatcher.toString())

        return true
    }


    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        val dao = GitHubUserRoomDB.getDb(context as Context).userDao()

        return when (sUriMatcher.match(uri)) {
            all -> {
                dao.selectCursor()
            }
            else -> null

        }
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return 0
    }
}