package com.readthefuckingmanual.fuckukk.data.model.user

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListUserAdminResponse(
	val values: List<UserAdminModel?>? = null,
	val status: String? = null
): Parcelable
