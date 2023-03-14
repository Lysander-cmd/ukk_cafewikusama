package www.smktelkommalang.sch.id.ukk_cafewikusama_kt.data.model.menu

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class MenuModel(
    var deskripsi: String?,
    var filename: String?,
    var harga: String,
    var id_menu: Int?,
    var jenis: String?,
    var nama_menu: String?,
    var path: String?
) : Parcelable