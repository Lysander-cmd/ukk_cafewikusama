package www.smktelkommalang.sch.id.ukk_cafewikusama_kt.data.model.transaksi

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class TransaksiModel(
    var id_meja: Int?,
    var id_transaksi: Int?,
    var id_user: Int?,
    var nama_pelanggan: String?,
    var status: String?,
    var tgl_transaksi: String?
) : Parcelable