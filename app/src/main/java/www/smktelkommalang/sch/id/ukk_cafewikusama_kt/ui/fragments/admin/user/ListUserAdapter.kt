package com.readthefuckingmanual.fuckukk.ui.fragments.admin.user

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.readthefuckingmanual.fuckukk.data.model.user.UserAdminModel
import com.readthefuckingmanual.fuckukk.databinding.ItemAdminUserBinding

class ListUserAdapter(
    private val observeSelectedUser: () -> Unit
) : RecyclerView.Adapter<ListUserAdapter.ListUserViewHolder>(){

    private var userList: ArrayList<UserAdminModel> = arrayListOf()

    fun setData(data : List<UserAdminModel>){
        userList.apply {
            clear()
            addAll(data)
        }
    }

    inner class ListUserViewHolder(private val binding: ItemAdminUserBinding, private val observeSelectedUser: () -> Unit) : RecyclerView.ViewHolder(binding.root) {
        fun bind(userItem: UserAdminModel){
            binding.apply {
                tvAdminNamaUser.text = userItem.namaUser

                root.setOnClickListener {
                    observeSelectedUser()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListUserViewHolder {
        val viewBinding = ItemAdminUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListUserViewHolder(viewBinding, observeSelectedUser)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: ListUserViewHolder, position: Int) {
        val item= userList[position]
        holder.bind(item)
    }

}