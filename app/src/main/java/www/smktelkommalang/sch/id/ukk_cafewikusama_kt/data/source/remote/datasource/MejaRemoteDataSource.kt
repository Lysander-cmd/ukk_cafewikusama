package com.readthefuckingmanual.fuckukk.data.source.remote.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.readthefuckingmanual.fuckukk.data.model.meja.ListMejaResponse
import com.readthefuckingmanual.fuckukk.data.model.menu.MenuModel
import com.readthefuckingmanual.fuckukk.data.source.remote.BasicResponse
import com.readthefuckingmanual.fuckukk.data.source.remote.retrofit.RetrofitConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object MejaRemoteDataSource {

    private val _mejaList : MutableLiveData<ListMejaResponse?> = MutableLiveData()
    val mejaList : MutableLiveData<ListMejaResponse?> = _mejaList

    private val _addmejarespons :MutableLiveData<BasicResponse?> = MutableLiveData()
    val mejaResponse : MutableLiveData<BasicResponse?> = _addmejarespons

    fun getListMeja(token : String){
        RetrofitConfig.ApiService.getAllMeja("Bearer $token")?.enqueue(
            object : Callback<ListMejaResponse?> {
                override fun onResponse(
                    call: Call<ListMejaResponse?>,
                    response: Response<ListMejaResponse?>
                ) {

                        if (response.isSuccessful){
                            _mejaList.value = response.body()
                        }
                }

                override fun onFailure(call: retrofit2.Call<ListMejaResponse?>, t: Throwable) {
                    _mejaList.postValue(null)
                }
            })
    }

    fun addMeja(token : String, nomeja: String)  {
       RetrofitConfig.ApiService.addMeja(token, nomeja).enqueue( object :Callback<BasicResponse?> {
           override fun onResponse(call: Call<BasicResponse?>, response: Response<BasicResponse?>) {
               if (response.isSuccessful){
                   _addmejarespons.value = response.body()
               }
           }

           override fun onFailure(call: Call<BasicResponse?>, t: Throwable) {
               _addmejarespons.postValue(null)
           }

       }

       )
    }

}