package www.smktelkommalang.sch.id.ukk_cafewikusama_kt.data.model.user

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class UserAdminModel (
        val role: String? = null,
        val idUser: Int? = null,
        val namaUser: String? = null,
        val email: String? = null,
        val username: String? = null
): Parcelable