package com.readthefuckingmanual.fuckukk.ui.fragments.cashier.transaksi

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.readthefuckingmanual.fuckukk.R
import com.readthefuckingmanual.fuckukk.data.repository.MenuRepository
import com.readthefuckingmanual.fuckukk.data.repository.TransaksiRepository
import com.readthefuckingmanual.fuckukk.data.source.preferences.UserPreferences
import com.readthefuckingmanual.fuckukk.data.source.remote.datasource.MejaRemoteDataSource
import com.readthefuckingmanual.fuckukk.databinding.FragmentTransaksiBinding
import com.readthefuckingmanual.fuckukk.ui.activities.main.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FragmentTransaksi : Fragment() {

    private var selectedMejaId: Int = 0
    private var binding : FragmentTransaksiBinding? = null
    private var rvItemListAdapter : ListItemTransactionAdapter? = null

    private val userPreference by lazy {
        UserPreferences(requireContext())
    }

    private val userToken by lazy {
        userPreference.getSession().token
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        rvItemListAdapter = ListItemTransactionAdapter()

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
         binding = FragmentTransaksiBinding.inflate(layoutInflater, container, false)

        return binding?.root
    }

    var totalharga = 0
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        MenuRepository.keranjang.observe(viewLifecycleOwner){
            rvItemListAdapter?.setData(it)
            totalharga = 0 // mulai dari 0 yaa
            for (i in it){
                totalharga += i.harga!!.toInt()
            }
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main){
                binding?.apply {

                    tvTotal.text = "Rp. $totalharga"

                }
            }

        }
        setupSpinner()

        setuprvItem()

        validateCheckout()

        setUpBtnCheckout()
    }

    fun setupSpinner(){
//        var listMejaAvailable: List<MejaModel?>? = listOf()
        MejaRemoteDataSource.mejaList.observe(viewLifecycleOwner) {listMeja ->
            val listMejaAvailable = listMeja?.values?.filter {
                it?.available == 1
            }
//            listMejaAvailable?.filter {
//                it?.available == 1
//            }
            val adapter = ArrayAdapter(requireContext(), R.layout.item_spinner_meja, listMejaAvailable!!.map {
                it?.nomor_meja
            })
            adapter.setDropDownViewResource(R.layout.item_spinner_meja)
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main){
                Log.d("TAG", "onViewCreated: Spinner")
                adapter.apply {
                    clear()
                    addAll(listMejaAvailable!!.map {
                        it?.nomor_meja
                    })
                    notifyDataSetChanged()
                    binding?.apply {
                        edtIndustriUsahaSpinner.adapter = adapter
                        edtIndustriUsahaSpinner.onItemSelectedListener = object :
                            AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                p0: AdapterView<*>?,
                                p1: View?,
                                p2: Int,
                                p3: Long
                            ) {
                                val selection = edtIndustriUsahaSpinner.selectedItem
                                val selecedIdMeja = listMejaAvailable!!.first {
                                    it?.nomor_meja == selection
                                }?.id_meja
                                if (selecedIdMeja != null) {
                                    selectedMejaId = selecedIdMeja
                                }
                            }

                            override fun onNothingSelected(p0: AdapterView<*>?) {

                            }

                        }

                    }
                }

            }
        }
    }

    fun setuprvItem(){
       binding?.rvItemKeranjang.apply {
           this?.layoutManager = LinearLayoutManager(requireContext())
           this?.adapter = rvItemListAdapter
       }
    }

    fun validateCheckout(){
        //if nama pelanggan kosong disable btn checkout
        //if nama pelanggan tidak kosong enable btn checkout
        binding?.edtNamaPelanggan?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding?.btnCheckout?.isEnabled = s.toString().isNotEmpty()
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
    }
    fun setUpBtnCheckout(){

        // assign the item id depends on the list of item inside the keranjang

        binding?.btnCheckout?.setOnClickListener {
            val _namaPelanggan :String = binding?.edtNamaPelanggan?.text.toString()
            TransaksiRepository.addTransaksi(
                token = userToken!!,
                id_meja = selectedMejaId,
                status = "belum_bayar", //default status
                 nama_pelanggan = _namaPelanggan,
                MenuRepository.keranjang.value!![0].id_menu!!,
                // if the list of item inside the keranjang is more than 1 then add the item id if it is not then just add null
                if (MenuRepository.keranjang.value!!.size > 1) MenuRepository.keranjang.value!![1].id_menu!! else null,
                if (MenuRepository.keranjang.value!!.size > 2) MenuRepository.keranjang.value!![2].id_menu!! else null,
                if (MenuRepository.keranjang.value!!.size > 3) MenuRepository.keranjang.value!![3].id_menu!! else null,
                if (MenuRepository.keranjang.value!!.size > 4) MenuRepository.keranjang.value!![4].id_menu!! else null,
                if (MenuRepository.keranjang.value!!.size > 5) MenuRepository.keranjang.value!![5].id_menu!! else null,
                if (MenuRepository.keranjang.value!!.size > 6) MenuRepository.keranjang.value!![6].id_menu!! else null,
                if (MenuRepository.keranjang.value!!.size > 7) MenuRepository.keranjang.value!![7].id_menu!! else null,
                if (MenuRepository.keranjang.value!!.size > 8) MenuRepository.keranjang.value!![8].id_menu!! else null,
                if (MenuRepository.keranjang.value!!.size > 9) MenuRepository.keranjang.value!![9].id_menu!! else null,
            ).observe(viewLifecycleOwner){
                if (it != null){
                    viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main){

                        Toast.makeText(requireContext(), "${it.message}", Toast.LENGTH_SHORT).show()
                        MenuRepository.keranjang.value?.clear()
                        MejaRemoteDataSource.getListMeja(userToken!!)
                        (activity as MainActivity).moveToMenuFragment()
                    }

                }
            }
        }
    }
    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            FragmentTransaksi()

    }
}