package www.smktelkommalang.sch.id.ukk_cafewikusama_kt.data.repository

import androidx.lifecycle.LiveData
import www.smktelkommalang.sch.id.ukk_cafewikusama_kt.data.model.user.UserModel
import www.smktelkommalang.sch.id.ukk_cafewikusama_kt.data.source.remote.datasource.AuthRemoteDataSource

object AuthRepository {
    fun doLogin(email : String, password : String)  : LiveData<UserModel?>{
        AuthRemoteDataSource.apply {
            doLogin(email, password)
            return userLogin
        }
    }
}