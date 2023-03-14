package com.readthefuckingmanual.fuckukk.ui.fragments.cashier.transaksi

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.view.menu.ListMenuItemView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.readthefuckingmanual.fuckukk.data.model.menu.MenuModel
import com.readthefuckingmanual.fuckukk.databinding.ItemCashierTransactionBinding
import com.readthefuckingmanual.fuckukk.ui.fragments.cashier.menu.ListMenuAdapter

class ListItemTransactionAdapter : RecyclerView.Adapter<ListItemTransactionAdapter.ListItemTransactionViewHolder>(){

    private var transactionItemList : ArrayList<MenuModel> = arrayListOf()

    fun setData(data : List<MenuModel>){
        transactionItemList.apply {
            clear()
            addAll(data)
            notifyDataSetChanged()
        }
    }

    inner class ListItemTransactionViewHolder(private val binding : ItemCashierTransactionBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(itemListBarang :MenuModel){
            binding.apply {
                binding.apply{
                    tvTransactionNamaMenu.text = itemListBarang.nama_menu
                    tvTransactionHargaMenu.text = "Rp ${itemListBarang.harga}"
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListItemTransactionAdapter.ListItemTransactionViewHolder {
        //TODO
        val viewBinding =
            ItemCashierTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListItemTransactionViewHolder(viewBinding)
    }

    override fun getItemCount(): Int {
        return transactionItemList.size
    }

    override fun onBindViewHolder(holder: ListItemTransactionAdapter.ListItemTransactionViewHolder, position: Int) {

        val item = transactionItemList[position]
        holder.bind(item)
    }
}