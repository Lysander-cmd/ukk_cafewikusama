package com.readthefuckingmanual.fuckukk.data.source.preferences

import android.content.Context
import com.readthefuckingmanual.fuckukk.data.model.user.UserModel

class UserPreferences(mContext : Context) {

    companion object{
        const val prefName = "usr_login"
        const val id_key = "usr_id"
        const val username_key = "usr_name"
        const val email_key = "usr_email"
        const val token_key = "usr_token"
        const val role_key = "usr_role"
    }

    private val preferences = mContext.getSharedPreferences(prefName, Context.MODE_PRIVATE)
    private val editor = preferences.edit()

    fun deleteSession(){
        editor.apply {
            clear()
            apply()
            commit()
        }
    }

    fun saveSession(userData : UserModel){
        editor.apply {
            putInt(id_key, userData.id_user!!)
            putString(username_key, userData.username)
            putString(email_key, userData.email)
            putString(token_key, userData.token)
            putString(role_key, userData.role)
            apply()
            commit()
        }

    }


    fun getSession() : UserModel {
        val id = preferences.getInt(id_key, 0)
        val name = preferences.getString(username_key, "")
        val email = preferences.getString(email_key, "")
        val token = preferences.getString(token_key, "")
        val role = preferences.getString(role_key, "")
        return UserModel(
            id_user = id,
            username = name,
            email = email,
            token = token,
            role = role,
            success = true,
            message = "Success User From Preferences"
        )
    }
}