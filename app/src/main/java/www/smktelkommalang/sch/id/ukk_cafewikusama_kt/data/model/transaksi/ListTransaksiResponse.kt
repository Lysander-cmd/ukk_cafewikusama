package com.readthefuckingmanual.fuckukk.data.model.transaksi

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListTransaksiResponse(
    var status: String?,
    var values: List<TransaksiModel?>?
): Parcelable
