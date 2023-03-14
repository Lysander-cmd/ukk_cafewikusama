package com.readthefuckingmanual.fuckukk.ui.fragments.cashier.history

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.readthefuckingmanual.fuckukk.data.repository.TransaksiRepository
import com.readthefuckingmanual.fuckukk.data.source.preferences.UserPreferences
import com.readthefuckingmanual.fuckukk.databinding.DialogDetailTransaksiBinding
import com.readthefuckingmanual.fuckukk.databinding.FragmentHistoryBinding
import com.readthefuckingmanual.fuckukk.ui.activities.login.LoginActivity
import com.readthefuckingmanual.fuckukk.ui.activities.main.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FragmentHistory : Fragment() {


    private var binding: FragmentHistoryBinding? = null
    private var rvHistoryAdapter: ListHistoryAdapter? = null

    private val userPreference by lazy {
        UserPreferences(requireContext())
    }

    private val userToken by lazy {
        userPreference.getSession().token
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rvHistoryAdapter = ListHistoryAdapter(::observeSelectedHistory)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDataHistory()
        setupRvHistory()
        setupBtnLogout()
        binding?.tvStatusCashierName?.text = userPreference.getSession().username
        setupFilter()
    }

    fun setupDataHistory(isFiltered : Boolean = false){
        TransaksiRepository.getListTransaksi(userToken!!).observe(viewLifecycleOwner) { listHistory ->
            val data = listHistory?.values?.reversed()
            if (isFiltered){
                rvHistoryAdapter?.setData(data?.filter { item -> item?.status == "belum_bayar" })
            }else{
                rvHistoryAdapter?.setData(data)
            }


        }
    }

    fun setupFilter(){
        binding?.switchCashierStatus?.apply {
            setOnCheckedChangeListener { buttonView, isChecked ->
                if(isChecked){
                    setupDataHistory(true)
                }else{
                    setupDataHistory()
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHistoryBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    fun setupRvHistory() {
        binding?.rvHistoryTransaksi?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = rvHistoryAdapter
        }
    }

    private fun observeSelectedHistory(idTransaksi: Int) {
        showDetailTransaksi(idTransaksi)
    }

    private fun showDetailTransaksi(idTransaksi: Int) {
        // Call getDetailTransaksi() and observe the response
        val dialogBinding =
            DialogDetailTransaksiBinding.inflate(layoutInflater, binding?.root, false)
        val dialog = AlertDialog.Builder(requireContext())
        TransaksiRepository.getDetailTransaksi(userToken!!, idTransaksi)
            .observe(viewLifecycleOwner) { detailTransaksi ->
                // Show the detail transaksi in a dialog
                var totalHarga = 0
                //count total harga
                for (i in detailTransaksi?.barang!!) {
                    totalHarga += i.harga!!.toInt()
                }
                viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
                    dialogBinding.apply {
                        tvDialogDetailTransaksiId.text =
                            "ID Meja : " + detailTransaksi?.id_meja.toString()
                        tvDialogDetailTransaksiTanggal.text = "Tanggal : "
                        detailTransaksi.tgl_transaksi?.replace("T", " ")

                        rvListItemTransaksi.apply {
                            layoutManager = LinearLayoutManager(requireContext())
                            adapter = ListItemTransaksiDetailAdapter().apply {
                                setData(detailTransaksi.barang)
                            }
                        }

                        tvDialogDetailTransaksiStatus.text = "Status : " + detailTransaksi?.status

                        tvDialogDetailTransaksiTotal.text =
                            "Total Harga : Rp" + totalHarga.toString()

                        tvDialogDetailTransaksiNamaPelanggan.text =
                            "Atas Nama : " + detailTransaksi?.nama_pelanggan

                        // if the transaction status is "belum_bayar", show the "selesaikan transaksi" button

                        if (detailTransaksi?.status == "belum_bayar") {
                            btnDialogDetailTransaksiSelesaikan.visibility = View.VISIBLE
                            btnDialogDetailTransaksiSelesaikan.setOnClickListener {
                                //TODO handle the "selesaikan transaksi" button click
                                var jumlahBarang = detailTransaksi.barang?.size
                                // check jumlah barang, then insert into the method update transaksi, if jumlah barang = 1, then insert 1 parameter, if 2, then insert 2 parameter, and so on leave the rest null
                                TransaksiRepository.updateTransaksi(
                                    userToken!!,
                                    id_transaksi = idTransaksi,
                                    id_meja = detailTransaksi.id_meja!!,
                                    status = "lunas",
                                    nama_pelanggan = detailTransaksi.nama_pelanggan!!,
                                    detailTransaksi.barang?.get(0)?.id_menu!!,
                                    if (jumlahBarang!! > 1) detailTransaksi.barang?.get(1)?.id_menu else null,
                                    if (jumlahBarang > 2) detailTransaksi.barang?.get(2)?.id_menu else null,
                                    if (jumlahBarang > 3) detailTransaksi.barang?.get(3)?.id_menu else null,
                                    if (jumlahBarang > 4) detailTransaksi.barang?.get(4)?.id_menu else null,
                                    if (jumlahBarang > 5) detailTransaksi.barang?.get(5)?.id_menu else null,
                                    if (jumlahBarang > 6) detailTransaksi.barang?.get(6)?.id_menu else null,
                                    if (jumlahBarang > 7) detailTransaksi.barang?.get(7)?.id_menu else null,
                                    if (jumlahBarang > 8) detailTransaksi.barang?.get(8)?.id_menu else null,
                                    if (jumlahBarang > 9) detailTransaksi.barang?.get(9)?.id_menu else null,

                                    ).observe(viewLifecycleOwner) {
                                    viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
                                        tvDialogDetailTransaksiStatus.text =
                                            "Status : " + it?.status
                                        if (it?.status == "lunas") {
                                            btnDialogDetailTransaksiSelesaikan.visibility =
                                                View.GONE
                                            setupDataHistory()
//                                            (activity as MainActivity).apply {
//                                                moveToMenuFragment()
//                                            moveToHistory()
//                                            }

                                        }

                                    }

                                }
                            }
                        } else {
                            btnDialogDetailTransaksiSelesaikan.visibility = View.GONE
                        }
                    }

                    dialog.setView(dialogBinding.root)
                        .create()
                    // Remove the view from its parent before adding it to the dialog
                    val parent = dialogBinding.root.parent as? ViewGroup
                    parent?.removeView(dialogBinding.root)
                    dialog.show()
                }

            }
    }


    fun setupBtnLogout() {
        binding?.btnLogoutCashierHistory?.setOnClickListener {
            userPreference.deleteSession()
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            FragmentHistory()

    }
}