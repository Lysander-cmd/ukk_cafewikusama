package com.readthefuckingmanual.fuckukk.data.model.menu

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailMenuResponse(
    var status: String?,
    var values: MenuModel?
): Parcelable