package com.readthefuckingmanual.fuckukk.data.repository

import androidx.lifecycle.MutableLiveData
import com.readthefuckingmanual.fuckukk.data.model.transaksi.DetailTransaksiModel
import com.readthefuckingmanual.fuckukk.data.model.transaksi.ListTransaksiResponse
import com.readthefuckingmanual.fuckukk.data.source.remote.datasource.TransaksiRemoteDataSource

object TransaksiRepository {

        fun getListTransaksi(token : String) : MutableLiveData<ListTransaksiResponse?> {

            TransaksiRemoteDataSource.apply {
                getListTransaksi(token)
                return transaksiList
            }
        }

        fun getDetailTransaksi(token : String, id : Int) : MutableLiveData<DetailTransaksiModel?> {
            TransaksiRemoteDataSource.apply {
                getDetailTransaksi(token, id)
                return detailTransaksi
            }
        }

//    fun updateTransaksi(token : String, id : Int, status : String) : MutableLiveData<DetailTransaksiModel?> {
//        TransaksiRemoteDataSource.apply {
//            updateTransaksi(token, id, status)
//            return detailUpdateTransaksi
//        }
//    }

    fun updateTransaksi(
        token : String,
        id_transaksi : Int,
        id_meja : Int, status : String, nama_pelanggan : String,
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
    ) : MutableLiveData<DetailTransaksiModel?> {
        TransaksiRemoteDataSource.apply {
            updateTransaksi(token, id_transaksi, id_meja, nama_pelanggan, status,
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
            )
            return detailUpdateTransaksi
        }
    }

    fun addTransaksi(
        token : String, id_meja : Int, status : String, nama_pelanggan : String,
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
    ) : MutableLiveData<DetailTransaksiModel?> {
        TransaksiRemoteDataSource.apply {
            addTransaksi(token, id_meja, nama_pelanggan, status,
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
            )
            return detailAddTransaksi
        }
    }

}