package com.app.consumer

import android.database.Cursor
import android.util.Log
import java.util.*

object MapHelper {
    fun mapCursorToArrayList(notesCursor: Cursor?): List<GitUserFav> {
        Log.d("Main_ContentResolve", notesCursor.toString())
        val notesList = ArrayList<GitUserFav>()
        notesCursor?.apply {
            while (moveToNext()) {
                Log.d("Main_ContentResolve", notesCursor.toString())
            }
        }
        return notesList
    }
}