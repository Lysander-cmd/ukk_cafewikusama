package www.smktelkommalang.sch.id.ukk_cafewikusama_kt.data.source.remote.datasource

import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import www.smktelkommalang.sch.id.ukk_cafewikusama_kt.data.model.user.ListUserAdminResponse
import www.smktelkommalang.sch.id.ukk_cafewikusama_kt.data.source.remote.retrofit.RetrofitConfig

object UserRemoteDataSource {

    private val _userList : MutableLiveData<ListUserAdminResponse?> = MutableLiveData()
    val userList : MutableLiveData<ListUserAdminResponse?> = _userList

    fun getListUser(){
        RetrofitConfig.ApiService.getAllUser()?.enqueue(
            object : Callback<ListUserAdminResponse?> {
                override fun onResponse(
                    call: Call<ListUserAdminResponse?>,
                    response: Response<ListUserAdminResponse?>
                ) {
                    if(response.isSuccessful){
                        _userList.value = response.body()
                    }
                }

                override fun onFailure(call: Call<ListUserAdminResponse?>, t: Throwable) {
                    _userList.postValue(null)
                }

            }
        )
    }
}