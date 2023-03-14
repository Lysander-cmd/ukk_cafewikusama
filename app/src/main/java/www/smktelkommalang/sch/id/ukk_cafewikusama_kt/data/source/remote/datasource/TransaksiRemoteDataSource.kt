package com.readthefuckingmanual.fuckukk.data.source.remote.datasource

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.readthefuckingmanual.fuckukk.data.model.transaksi.DetailTransaksiModel
import com.readthefuckingmanual.fuckukk.data.model.transaksi.ListTransaksiResponse
import com.readthefuckingmanual.fuckukk.data.source.remote.retrofit.RetrofitConfig
import retrofit2.Call
import retrofit2.Callback

object TransaksiRemoteDataSource {

    private val _transaksiList : MutableLiveData<ListTransaksiResponse?> = MutableLiveData()
    val transaksiList : MutableLiveData<ListTransaksiResponse?> = _transaksiList

    private val _detailTransaksi : MutableLiveData<DetailTransaksiModel?> = MutableLiveData()
    val detailTransaksi : MutableLiveData<DetailTransaksiModel?> = _detailTransaksi

    private val _detailUpdateTransaksi : MutableLiveData<DetailTransaksiModel?> = MutableLiveData()
    val detailUpdateTransaksi : MutableLiveData<DetailTransaksiModel?> = _detailUpdateTransaksi


    private val _detailAddTransaksi : MutableLiveData<DetailTransaksiModel?> = MutableLiveData()
    val detailAddTransaksi : MutableLiveData<DetailTransaksiModel?> = _detailAddTransaksi

    fun getListTransaksi(token : String){
     RetrofitConfig.ApiService.getAllTransaksi("Bearer $token")?.enqueue(
            object : Callback<ListTransaksiResponse?> {
                override fun onResponse(
                    call: retrofit2.Call<ListTransaksiResponse?>,
                    response: retrofit2.Response<ListTransaksiResponse?>
                ) {
                    if (response.isSuccessful){
                        _transaksiList.value = response.body()
                    }
                }

                override fun onFailure(call: retrofit2.Call<ListTransaksiResponse?>, t: Throwable) {
                    _transaksiList.postValue(null)
                }
            })
    }

    fun getDetailTransaksi(token : String, id : Int){
        RetrofitConfig.ApiService.getTransaksiById("Bearer $token", id)?.enqueue(
            object : Callback<DetailTransaksiModel?> {
                override fun onResponse(
                    call: Call<DetailTransaksiModel?>,
                    response: retrofit2.Response<DetailTransaksiModel?>
                ) {
                    if (response.isSuccessful){
                        _detailTransaksi.value = response.body()
                    }
                }

                override fun onFailure(call: Call<DetailTransaksiModel?>, t: Throwable) {
                    _detailTransaksi.postValue(null)
                }
            })
    }

    fun updateTransaksi(token : String, id : Int, status : String){
        RetrofitConfig.ApiService.updateTransaksi("Bearer $token", id, status).enqueue(
            object : Callback<DetailTransaksiModel?> {
                override fun onResponse(
                    call: Call<DetailTransaksiModel?>,
                    response: retrofit2.Response<DetailTransaksiModel?>
                ) {
                    if (response.isSuccessful){
                        _detailUpdateTransaksi.value = response.body()
                    }
                }

                override fun onFailure(call: Call<DetailTransaksiModel?>, t: Throwable) {
                    _detailTransaksi.postValue(null)
                }
            })
    }

    fun updateTransaksi(
        token : String, id_transaksi : Int, id_meja : Int, nama_pelanggan : String, status : String,
        item1: Int,
        item2: Int?,
        item3: Int?,
        item4: Int?,
        item5: Int?,
        item6: Int?,
        item7: Int?,
        item8: Int?,
        item9: Int?,
        item10: Int?,
    ) {
        Log.d("TAG", "addTransaksi: $nama_pelanggan")
        RetrofitConfig.ApiService.updateTransaksi(
            "Bearer $token", id_transaksi, id_meja, nama_pelanggan, status,
            item1,
            item2,
            item3,
            item4,
            item5,
            item6,
            item7,
            item8,
            item9,
            item10
        ).enqueue(
            object : Callback<DetailTransaksiModel?> {
                override fun onResponse(
                    call: Call<DetailTransaksiModel?>,
                    response: retrofit2.Response<DetailTransaksiModel?>
                ) {
                    if (response.isSuccessful){
                        _detailUpdateTransaksi.value = response.body()
                    }
                }

                override fun onFailure(call: Call<DetailTransaksiModel?>, t: Throwable) {
                    _detailUpdateTransaksi.postValue(null)
                }
            })
    }

    fun addTransaksi(
        token : String, id_meja : Int, nama_pelanggan : String, status : String,
        item1: Int,
        item2: Int?,
        item3: Int?,
        item4: Int?,
        item5: Int?,
        item6: Int?,
        item7: Int?,
        item8: Int?,
        item9: Int?,
        item10: Int?,
    ) {
        Log.d("TAG", "addTransaksi: $nama_pelanggan")
        RetrofitConfig.ApiService.addTransaksi(
            "Bearer $token", id_meja, nama_pelanggan, status,
            item1,
            item2,
            item3,
            item4,
            item5,
            item6,
            item7,
            item8,
            item9,
            item10
        ).enqueue(
            object : Callback<DetailTransaksiModel?> {
                override fun onResponse(
                    call: Call<DetailTransaksiModel?>,
                    response: retrofit2.Response<DetailTransaksiModel?>
                ) {
                    if (response.isSuccessful){
                        _detailAddTransaksi.value = response.body()
                    }
                }

                override fun onFailure(call: Call<DetailTransaksiModel?>, t: Throwable) {
                    _detailAddTransaksi.postValue(null)
                }
            })
    }
}