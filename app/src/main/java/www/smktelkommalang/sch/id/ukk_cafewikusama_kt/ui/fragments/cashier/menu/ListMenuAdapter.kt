package com.readthefuckingmanual.fuckukk.ui.fragments.cashier.menu

import android.view.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.readthefuckingmanual.fuckukk.data.model.menu.MenuModel
import com.readthefuckingmanual.fuckukk.data.repository.MenuRepository
import com.readthefuckingmanual.fuckukk.databinding.ItemCashierMenuBinding

class ListMenuAdapter(
    private val observeSelectedMenu: () -> Unit
) : RecyclerView.Adapter<ListMenuAdapter.ListMenuViewHolder>() {

    private var menuList:ArrayList<MenuModel> = arrayListOf()
    fun setData(data : List<MenuModel>){
        menuList.apply {
            clear()
            addAll(data)
        }
        notifyDataSetChanged()
    }

    inner class ListMenuViewHolder(private val binding: ItemCashierMenuBinding,  private val observeSelectedMenu: () -> Unit) : RecyclerView.ViewHolder(binding.root) {
        fun bind(menuItem: MenuModel){

            binding.apply{
                tvNamaMenu.text = menuItem.nama_menu
                tvDeskripsiMenu.text = menuItem.deskripsi
                tvHargaMenu.text = menuItem.harga.toString()
                Glide.with(ivGambarMenu).load(menuItem.path).into(ivGambarMenu)

                MenuRepository.keranjang.value?.size.let {
                    if (it != null) {
                        if (it < 10) {
                            btnAddMenu.setOnClickListener {
                                MenuRepository.addToKeranjang(menuItem)
                                it.visibility = View.GONE
                                btnRemoveFromKeranjang.visibility = View.VISIBLE
                                observeSelectedMenu()
                            }
                            btnRemoveFromKeranjang.setOnClickListener {
                                MenuRepository.removeFromKeranjang(menuItem)
                                it.visibility = View.GONE
                                btnAddMenu.visibility = View.VISIBLE
                                observeSelectedMenu()
                            }
                        }else{
                            btnAddMenu.visibility = View.GONE
                            btnRemoveFromKeranjang.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListMenuAdapter.ListMenuViewHolder {
        val viewBinding =
            ItemCashierMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListMenuViewHolder(viewBinding, observeSelectedMenu)
    }

    override fun onBindViewHolder(holder: ListMenuAdapter.ListMenuViewHolder, position: Int) {
        val item = menuList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return menuList.size
    }
}