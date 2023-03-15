package www.smktelkommalang.sch.id.ukk_cafewikusama_kt.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import www.smktelkommalang.sch.id.ukk_cafewikusama_kt.data.source.remote.BasicResponse
import www.smktelkommalang.sch.id.ukk_cafewikusama_kt.data.source.remote.datasource.MejaRemoteDataSource

object MejaRepository {

    fun addMeja(token: String, noMeja: String): MutableLiveData<BasicResponse?> {
        MejaRemoteDataSource.apply {
            addMeja(token, noMeja)
            return mejaResponse
        }

//    private val _listMeja = MutableLiveData<ListMejaResponse?>()
//    val listMeja get() = _listMeja
//
//    fun getListMeja(token : String) {
//        val mejaList = MutableLiveData<ListMejaResponse?>()
//        MejaRemoteDataSource.apply {
//            getListMeja(token)
//            _listMeja.
//
//            value = mejaList.value
//        }
//    }
    }
}