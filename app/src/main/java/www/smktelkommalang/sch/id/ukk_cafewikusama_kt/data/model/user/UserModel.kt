package com.readthefuckingmanual.fuckukk.data.model.user

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    var email: String?,
    var id_user: Int?,
    var message: String?,
    var role: String?,
    var success: Boolean?,
    var token: String?,
    var username: String?
) : Parcelable