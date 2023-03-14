package www.smktelkommalang.sch.id.ukk_cafewikusama_kt.data.model.menu

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailMenuResponse(
    var status: String?,
    var values: MenuModel?
): Parcelable