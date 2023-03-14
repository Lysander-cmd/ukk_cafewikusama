package www.smktelkommalang.sch.id.ukk_cafewikusama_kt.ui.fragments.cashier.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import www.smktelkommalang.sch.id.ukk_cafewikusama_kt.data.model.menu.MenuModel
import www.smktelkommalang.sch.id.ukk_cafewikusama_kt.databinding.ItemCashierTransactionBinding

class ListItemTransaksiDetailAdapter : RecyclerView.Adapter<ListItemTransaksiDetailAdapter.ListItemDetailTransaksiViewHolder>() {
    private val listItemData : ArrayList<MenuModel> = arrayListOf()

    fun setData(data : List<MenuModel?>?){
        listItemData.apply {
            clear()
            addAll(data as Collection<MenuModel>)
            notifyDataSetChanged()
        }
    }
    inner class ListItemDetailTransaksiViewHolder(private val binding : ItemCashierTransactionBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(itemListBarang :MenuModel){
            binding.apply {
                binding.apply{
                    tvTransactionNamaMenu.text = itemListBarang.nama_menu
                    tvTransactionHargaMenu.text = "Rp ${itemListBarang.harga}"
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemDetailTransaksiViewHolder {
        val viewBinding =
            ItemCashierTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListItemDetailTransaksiViewHolder(viewBinding)
    }

    override fun getItemCount(): Int {
        return listItemData.size
    }

    override fun onBindViewHolder(holder: ListItemDetailTransaksiViewHolder, position: Int) {
        val item = listItemData[position]
        holder.bind(item)
    }

}