package www.smktelkommalang.sch.id.ukk_cafewikusama_kt.data.model.transaksi

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListTransaksiResponse(
    var status: String?,
    var values: List<TransaksiModel?>?
): Parcelable
