package com.readthefuckingmanual.fuckukk.data.model.meja

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class MejaModel(
    var id_meja: Int?,
    var nomor_meja: String?,
    var available : Int?
) : Parcelable