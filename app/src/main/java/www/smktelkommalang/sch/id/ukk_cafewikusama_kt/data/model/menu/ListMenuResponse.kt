package com.readthefuckingmanual.fuckukk.data.model.menu

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListMenuResponse(
    var status: String?,
    var values: List<MenuModel?>?
): Parcelable