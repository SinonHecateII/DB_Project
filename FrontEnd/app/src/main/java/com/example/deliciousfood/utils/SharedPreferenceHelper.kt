package com.example.deliciousfood.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE

object SharedPreferenceHelper {
    private const val PREF_APP = "DELICIOUS_APP"
    private const val SP_ID = "SP_ID"
    private const val SP_Nickname = "SP_Nickname";
    private const val SP_PW = "SP_PW";
    private const val SP_Email = "SP_Email";

    // 로그인한 아이디
    fun setLoginID(context: Context, id: String) {
        val pref = context.getSharedPreferences(PREF_APP, MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString(SP_ID, id)
        editor.commit()
    }

    // 로그인한 비밀번호
    fun setLoginPW(context: Context, pw: String) {
        val pref = context.getSharedPreferences(PREF_APP, MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString(SP_PW, pw)
        editor.commit()
    }

    // 로그인한 이메일
    fun setLoginEmail(context: Context, email: String) {
        val pref = context.getSharedPreferences(PREF_APP, MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString(SP_Email, email)
        editor.commit()
    }

    // 유저 닉네임
    fun setNickname(context: Context, name: String) {
        val pref = context.getSharedPreferences(PREF_APP, MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString(SP_Nickname, name)
        editor.commit()
    }

    // 로그인한 아이디 받기
    fun getLoginID(context: Context): String? {
        val pref = context.getSharedPreferences(PREF_APP, MODE_PRIVATE)
        return pref.getString(SP_ID, "")
    }

    // 로그인한 비밀번호 받기
    fun getLoginPW(context: Context): String? {
        val pref = context.getSharedPreferences(PREF_APP, MODE_PRIVATE)
        return pref.getString(SP_PW, "")
    }

    // 로그인한 이메일 받기
    fun getLoginEmail(context: Context): String? {
        val pref = context.getSharedPreferences(PREF_APP, MODE_PRIVATE)
        return pref.getString(SP_Email, "")
    }

    // 우저 닉네임 받기
    fun getNickname(context: Context): String? {
        val pref = context.getSharedPreferences(PREF_APP, MODE_PRIVATE)
        return pref.getString(SP_Nickname, "")
    }
}