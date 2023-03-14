package com.readthefuckingmanual.fuckukk.data.source.remote.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.readthefuckingmanual.fuckukk.data.model.user.UserModel
import com.readthefuckingmanual.fuckukk.data.source.remote.retrofit.RetrofitConfig

object AuthRemoteDataSource {

    private val _userLogin : MutableLiveData<UserModel?> = MutableLiveData()
    val userLogin : LiveData<UserModel?> = _userLogin
    fun doLogin(email : String, password : String) {
        RetrofitConfig.ApiService.doLogin(email, password).enqueue(
            object : retrofit2.Callback<UserModel>{
                override fun onResponse(
                    call: retrofit2.Call<UserModel>,
                    response: retrofit2.Response<UserModel>
                ) {
                    if (response.isSuccessful){
                        _userLogin.value = response.body()
                    }
                }

                override fun onFailure(call: retrofit2.Call<UserModel>, t: Throwable) {
                    _userLogin.postValue(null)
                }
            }
        )
    }

}