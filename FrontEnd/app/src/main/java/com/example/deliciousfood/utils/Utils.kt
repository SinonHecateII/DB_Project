package com.example.deliciousfood.utils

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import androidx.loader.content.CursorLoader

object Utils {

    // uri 절대경로 가져오기
    fun getPath(context: Context, uri: Uri): String? {
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursorLoader = CursorLoader(context, uri, proj, null, null, null)
        val cursor = cursorLoader.loadInBackground()!!
        val index: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        return cursor.getString(index)
    }
}