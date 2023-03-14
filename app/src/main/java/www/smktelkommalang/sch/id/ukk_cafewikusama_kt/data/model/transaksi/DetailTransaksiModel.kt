package www.smktelkommalang.sch.id.ukk_cafewikusama_kt.data.model.transaksi

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import www.smktelkommalang.sch.id.ukk_cafewikusama_kt.data.model.menu.MenuModel

@Parcelize
data class DetailTransaksiModel(
    var barang: List<MenuModel>?,
    var id_meja: Int?,
    var message: String?,
    var nama_pelanggan: String?,
    var status: String?,
    var tgl_transaksi: String?,
    var total_harga: Int?,
    var user: Int?
) : Parcelable