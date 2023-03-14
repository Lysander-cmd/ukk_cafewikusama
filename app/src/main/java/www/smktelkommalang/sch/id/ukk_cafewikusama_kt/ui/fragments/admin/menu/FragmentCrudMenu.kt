package com.readthefuckingmanual.fuckukk.ui.fragments.admin.menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.readthefuckingmanual.fuckukk.data.model.menu.MenuModel
import com.readthefuckingmanual.fuckukk.data.repository.MenuRepository
import com.readthefuckingmanual.fuckukk.data.source.preferences.UserPreferences
import com.readthefuckingmanual.fuckukk.databinding.FragmentCrudMenuBinding
import com.readthefuckingmanual.fuckukk.databinding.FragmentMenuBinding
import com.readthefuckingmanual.fuckukk.ui.activities.admin.AdminActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"



/**
 * A simple [Fragment] subclass.
 * Use the [FragmentCrudMenu.newInstance] factory method to
 * create an instance of this fragment.
 */

class FragmentCrudMenu : Fragment() {

    private var binding: FragmentCrudMenuBinding? = null
    private var isedit = false
    private val userPreference by lazy {
        UserPreferences(requireContext())
    }

    private val userToken by lazy {
        userPreference.getSession().token
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCrudMenuBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeSelectedMenu()
        setupButtonSave()
    }

    fun observeSelectedMenu(){
        MenuRepository.selectedmenu.observe(viewLifecycleOwner){
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main){
                isedit = it != null
                if (isedit){
                    binding?.apply {
                        edtNamaMenu.setText(it?.nama_menu)
                        edtMenuType.setText(it?.jenis)
                        edtDescription.setText(it?.deskripsi)
                        edtPrice.setText(it?.harga)
                    }
                }else{
                    binding?.apply {
                        edtNamaMenu.text.clear()
                        edtMenuType.text.clear()
                        edtDescription.text.clear()
                        edtPrice.text.clear()
                    }
                }

            }

        }
    }

    fun setupButtonSave(){
        binding?.apply {
            btnMenuSave?.setOnClickListener() {

                val menuModel = MenuModel(
                    nama_menu = edtNamaMenu.text.toString(),
                    jenis = edtMenuType.text.toString(),
                    deskripsi = edtDescription.text.toString(),
                    harga = edtPrice.text.toString(),
                    filename = "",
                    id_menu = null,
                    path = "",
                )
                if (isedit) {
                    MenuRepository.addMenu(userToken!!, menuModel).observe(viewLifecycleOwner){
                        if (it != null){
                            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
                                Toast.makeText(requireContext(), "Menu Ditambahkan ${it?.nama_menu}", Toast.LENGTH_SHORT)
                                (activity as AdminActivity).moveToAdminMenuFragment()

                            }


                        }

                    }
                } else {
                    menuModel.apply {
                        filename = MenuRepository.selectedmenu.value?.filename
                        id_menu = MenuRepository.selectedmenu.value?.id_menu
                        path = MenuRepository.selectedmenu.value?.path
                    }
                    MenuRepository.edtMenu(userToken!!, menuModel).observe(viewLifecycleOwner){
                        if (it != null){
                            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
                                Toast.makeText(requireContext(), "Menu Diedit ${it?.nama_menu}", Toast.LENGTH_SHORT)
                                (activity as AdminActivity).moveToAdminMenuFragment()

                            }

                        }

                    }
                }


            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        MenuRepository.selectedmenu.postValue(null)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentCrudMenu.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            FragmentCrudMenu()

    }
}