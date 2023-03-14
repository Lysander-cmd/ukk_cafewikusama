package www.smktelkommalang.sch.id.ukk_cafewikusama_kt.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import www.smktelkommalang.sch.id.ukk_cafewikusama_kt.data.model.user.ListUserAdminResponse
import www.smktelkommalang.sch.id.ukk_cafewikusama_kt.data.model.user.UserAdminModel
import www.smktelkommalang.sch.id.ukk_cafewikusama_kt.data.source.remote.datasource.UserRemoteDataSource

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