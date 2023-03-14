package www.smktelkommalang.sch.id.ukk_cafewikusama_kt.data.model.meja

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListMejaResponse(
    var status: String?,
    var values: List<MejaModel?>?
): Parcelable