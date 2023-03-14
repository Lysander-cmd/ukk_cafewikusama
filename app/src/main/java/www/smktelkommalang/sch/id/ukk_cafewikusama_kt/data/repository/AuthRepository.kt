package com.readthefuckingmanual.fuckukk.data.repository

import androidx.lifecycle.LiveData
import com.readthefuckingmanual.fuckukk.data.model.user.UserModel
import com.readthefuckingmanual.fuckukk.data.source.remote.datasource.AuthRemoteDataSource

object AuthRepository {
    fun doLogin(email : String, password : String)  : LiveData<UserModel?>{
        AuthRemoteDataSource.apply {
            doLogin(email, password)
            return userLogin
        }
    }
}