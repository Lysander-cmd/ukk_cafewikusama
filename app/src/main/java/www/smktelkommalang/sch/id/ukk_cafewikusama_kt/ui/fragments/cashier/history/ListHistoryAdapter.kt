package com.readthefuckingmanual.fuckukk.ui.fragments.cashier.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.readthefuckingmanual.fuckukk.data.model.transaksi.TransaksiModel
import com.readthefuckingmanual.fuckukk.databinding.ItemCashierHistoryBinding

class ListHistoryAdapter(private val observeSelectedHistory: (Int) -> Unit) : RecyclerView.Adapter<ListHistoryAdapter.ListMenuViewHolder>() {

    private var historyList: ArrayList<TransaksiModel> = arrayListOf()

    fun setData(data: List<TransaksiModel?>?){
        historyList.apply {
            clear()
            addAll(data as Collection<TransaksiModel>)
            notifyDataSetChanged()
        }
    }



    inner class ListMenuViewHolder(private val binding: ItemCashierHistoryBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(historyItem: TransaksiModel){

            binding.apply {
                tvHistoryIdPesanan.text = "Id Transaksi : " + historyItem.id_transaksi.toString()
                tvHistoryTanggalPesanan.text = historyItem.tgl_transaksi?.replace("T", " ") ?: "".replace(".000Z", " ")
                tvHistoryStatusPembayaran.text = "Status : " + historyItem.status

                // Add click listener for the item view
//                root.setOnClickListener {
//                    // Call getDetailTransaksi to retrieve the transaction details
//                    if (historyItem.status == "belum_bayar") {
//                        TransaksiRepository.getDetailTransaksi(userToken!!, historyItem.id_transaksi!!)
//                            .observe(viewLifecycleOwner) { detailTransaksi ->
//                                // Show the dialog with the transaction details
//                                showDetailTransaksiDialog(detailTransaksi)
//                            }
//                    } else {
//                        // Show the dialog without calling getDetailTransaksi
//                        showDetailTransaksiDialog(null)
//                    }
//                }

            }
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListHistoryAdapter.ListMenuViewHolder {
        val viewBinding =
            ItemCashierHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListMenuViewHolder((viewBinding))
    }

    override fun onBindViewHolder(holder: ListHistoryAdapter.ListMenuViewHolder, position: Int) {
        val item = historyList[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            observeSelectedHistory(item.id_transaksi!!)
        }
    }

    override fun getItemCount(): Int {
        return historyList.size
    }


}