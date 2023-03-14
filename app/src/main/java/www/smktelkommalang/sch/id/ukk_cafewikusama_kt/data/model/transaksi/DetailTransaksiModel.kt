package com.readthefuckingmanual.fuckukk.data.model.transaksi

import android.os.Parcelable
import com.readthefuckingmanual.fuckukk.data.model.menu.MenuModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailTransaksiModel(
    var barang: List<MenuModel>?,
    var id_meja: Int?,
    var message: String?,
    var nama_pelanggan: String?,
    var status: String?,
    var tgl_transaksi: String?,
    var total_harga: Int?,
    var user: Int?
) : Parcelable