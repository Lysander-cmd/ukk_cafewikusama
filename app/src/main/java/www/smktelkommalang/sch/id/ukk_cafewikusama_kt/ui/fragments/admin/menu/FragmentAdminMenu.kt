package com.readthefuckingmanual.fuckukk.ui.fragments.admin.menu

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.readthefuckingmanual.fuckukk.R
import com.readthefuckingmanual.fuckukk.data.model.menu.MenuModel
import com.readthefuckingmanual.fuckukk.data.repository.MenuRepository
import com.readthefuckingmanual.fuckukk.data.source.preferences.UserPreferences
import com.readthefuckingmanual.fuckukk.databinding.FragmentAdminMenuBinding
import com.readthefuckingmanual.fuckukk.ui.activities.admin.AdminActivity
import com.readthefuckingmanual.fuckukk.ui.activities.login.LoginActivity
import com.readthefuckingmanual.fuckukk.ui.activities.main.MainActivity
import com.readthefuckingmanual.fuckukk.ui.fragments.cashier.menu.FragmentMenu
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FragmentAdminMenu : Fragment() {

    private var binding: FragmentAdminMenuBinding? = null

    private var rvAdminMenuAdaper: ListAdminMenuAdapter? = null

    private val userPreference by lazy {
        UserPreferences(requireContext())
    }

    private val userToken by lazy {
        userPreference.getSession().token
    }

    private var menu: List<MenuModel?>? = listOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rvAdminMenuAdaper = ListAdminMenuAdapter {menuModel: MenuModel ->  observeSelectedMenuAdmin(menuModel) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdminMenuBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        observeSelectedAdminMenu()
        MenuRepository.getAllMenus(userToken!!).observe(viewLifecycleOwner){
            if (it != null){
                rvAdminMenuAdaper?.setData(it.values as List<MenuModel>)
            }
        }

        setupRvMenu()
        setupBtnLogout()
        setupFAB()

    }

    fun setupFAB() {
        binding?.fabAddMenuAdmin?.setOnClickListener{
            (activity as AdminActivity).moveToCrudMenuFragment()
        }
    }

    fun setupBtnLogout() {
        binding?.btnLogoutAdminMenu?.setOnClickListener {
            userPreference.deleteSession()
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }


    fun setupRvMenu() {
        binding?.rvMenuAdmin?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = rvAdminMenuAdaper

        }

    }

    fun observeSelectedMenuAdmin(menuModel : MenuModel) {
        MenuRepository.selectedmenu.apply {
            postValue(menuModel)
            observe(viewLifecycleOwner){
                viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main){
                    Toast.makeText(requireContext(), "Menu Clicked ${it?.nama_menu}", Toast.LENGTH_SHORT)
                    if (it != null){

                        (activity as AdminActivity).moveToCrudMenuFragment()
                    }
                }
        }
        }

//        MenuRepository.selectedmenu.observe(viewLifecycleOwner) {
//            it = menuModel
//            }
        }


    companion object {
        @JvmStatic
        fun newInstance() =
            FragmentAdminMenu()
    }
}