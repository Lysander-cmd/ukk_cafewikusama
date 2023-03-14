package www.smktelkommalang.sch.id.ukk_cafewikusama_kt.ui.fragments.admin.menu

import android.view.LayoutInflater
import android.view.Menu
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import www.smktelkommalang.sch.id.ukk_cafewikusama_kt.data.model.menu.MenuModel
import www.smktelkommalang.sch.id.ukk_cafewikusama_kt.data.repository.MenuRepository
import www.smktelkommalang.sch.id.ukk_cafewikusama_kt.databinding.FragmentAdminMenuBinding
import www.smktelkommalang.sch.id.ukk_cafewikusama_kt.databinding.ItemAdminMenuBinding

class ListAdminMenuAdapter(
    private val observeSelectedMenu: (MenuModel) -> Unit
) : RecyclerView.Adapter<ListAdminMenuAdapter.ListAdminMenuViewHolder>() {

    fun setData(data: List<MenuModel>) {
        adminMenuList.apply {
            clear()
            addAll(data)
        }
        notifyDataSetChanged()
    }

    inner class ListAdminMenuViewHolder(
        private val binding: ItemAdminMenuBinding,
        private val observeSelectedMenu: (MenuModel) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(adminMenuItem: MenuModel) {
            binding.apply {
                tvAdminNamaMenu.text = adminMenuItem.nama_menu
                tvAdminDeskripsiMenu.text = adminMenuItem.deskripsi
                tvAdminHargaMenu.text = adminMenuItem.harga.toString()
                Glide.with(ivAdminGambarMenu).load(adminMenuItem.path).into(ivAdminGambarMenu)

                root.setOnClickListener {
                    observeSelectedMenu(adminMenuItem)
                }
            }
        }
    }

    private var adminMenuList: ArrayList<MenuModel> = arrayListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAdminMenuAdapter.ListAdminMenuViewHolder {
        val viewBinding = ItemAdminMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListAdminMenuViewHolder(viewBinding , observeSelectedMenu)
    }

    override fun getItemCount(): Int {
        return adminMenuList.size
    }

    override fun onBindViewHolder(holder: ListAdminMenuViewHolder, position: Int) {
        val item = adminMenuList[position]
        holder.bind(item)
    }


}
