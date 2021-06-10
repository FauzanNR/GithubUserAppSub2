package com.app.githubuserappsub2.provider

import android.database.Cursor
import android.util.Log
import com.app.githubuserappsub2.roomdb.GitUserDao
import java.util.*

object MapHelper {
    fun mapCursorToArrayList(notesCursor: Cursor?): List<GitUserDao> {
        Log.d("Main_ContentResolve", notesCursor.toString())
        val notesList = ArrayList<GitUserDao>()
        notesCursor?.apply {
            while (moveToNext()) {
                Log.d("Main_ContentResolve", notesCursor.toString())
            }
        }
        return notesList
    }
}