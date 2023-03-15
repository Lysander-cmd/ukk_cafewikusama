package www.smktelkommalang.sch.id.ukk_cafewikusama_kt.ui.fragments.admin.table

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import www.smktelkommalang.sch.id.ukk_cafewikusama_kt.data.model.meja.MejaModel
import www.smktelkommalang.sch.id.ukk_cafewikusama_kt.databinding.ItemAdminTableBinding
import www.smktelkommalang.sch.id.ukk_cafewikusama_kt.databinding.ItemCashierMenuBinding

class ListTableAdapter (
//      private val observeSelectedMeja: () -> Unit
) : RecyclerView.Adapter<ListTableAdapter.ListTableViewHolder>() {

    private var tableList:ArrayList<MejaModel> = arrayListOf()
    fun setData(data : List<MejaModel>){
        tableList.apply{
            clear()
            addAll(data)
        }
        notifyDataSetChanged()
    }

    inner class ListTableViewHolder(private val binding: ItemAdminTableBinding,
//                                        private val observeSelectedMeja: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root){
        fun bind(mejaItem: MejaModel){

            binding.apply {
                tvAdminNamaMeja.text = mejaItem.nomor_meja
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListTableAdapter.ListTableViewHolder {
        val viewBinding =
            ItemAdminTableBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListTableViewHolder(viewBinding) //observeSelectedMeja)
    }

    override fun onBindViewHolder(holder: ListTableAdapter.ListTableViewHolder, position: Int) {
        val item = tableList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return tableList.size
        }

    }
