package com.readthefuckingmanual.fuckukk.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.readthefuckingmanual.fuckukk.data.model.menu.MenuModel
import com.readthefuckingmanual.fuckukk.data.model.user.ListUserAdminResponse
import com.readthefuckingmanual.fuckukk.data.model.user.UserAdminModel
import com.readthefuckingmanual.fuckukk.data.source.remote.datasource.UserRemoteDataSource

object UserRepository {

    val selecteduser : MutableLiveData<UserAdminModel?> = MutableLiveData()
    val user : MutableLiveData<ArrayList<UserAdminModel>> = MutableLiveData(ArrayList())
    fun getAllUser() : LiveData<ListUserAdminResponse?>{
        UserRemoteDataSource.apply {
            getListUser()
            return userList
        }
    }
}