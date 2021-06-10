package com.app.githubuserappsub2.roomdb

import android.net.Uri

object DbUri {

    const val AUTHORITY = "com.app.githubuserappsub2"
    const val SCHEME = "content"

    class DbProperty {

        companion object {
            const val TABLE_NAME = "fav_user_table"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }

    }
}