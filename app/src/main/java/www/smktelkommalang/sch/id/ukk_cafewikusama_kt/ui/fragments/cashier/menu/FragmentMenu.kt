package com.readthefuckingmanual.fuckukk.ui.fragments.cashier.menu

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.readthefuckingmanual.fuckukk.R

import com.readthefuckingmanual.fuckukk.data.model.menu.MenuModel
import com.readthefuckingmanual.fuckukk.data.repository.MenuRepository
import com.readthefuckingmanual.fuckukk.data.source.preferences.UserPreferences
import com.readthefuckingmanual.fuckukk.databinding.FragmentMenuBinding
import com.readthefuckingmanual.fuckukk.ui.activities.login.LoginActivity
import com.readthefuckingmanual.fuckukk.ui.activities.main.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FragmentMenu : Fragment() {

    private var binding: FragmentMenuBinding? = null

    private var rvMenuMakananAdapter: ListMenuAdapter? = null
    private var rvMenuMinumanAdapter: ListMenuAdapter? = null

    private val userPreference by lazy {
        UserPreferences(requireContext())
    }

    private val userToken by lazy {
        userPreference.getSession().token
    }

    private var menuMakanan: List<MenuModel?>? = listOf()
    private var menuMinuman: List<MenuModel?>? = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rvMenuMakananAdapter = ListMenuAdapter { observeSelectedMenu() }
        rvMenuMinumanAdapter = ListMenuAdapter { observeSelectedMenu() }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMenuBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeSelectedMenu()
        MenuRepository.clearKeranjang()
        MenuRepository.getAllMenus(userToken!!).observe(viewLifecycleOwner) {
            menuMakanan = it?.values?.filter { menu -> menu?.jenis == "makanan" }
            menuMinuman = it?.values?.filter { menu -> menu?.jenis == "minuman" }
            rvMenuMakananAdapter?.setData(menuMakanan as List<MenuModel>)
            rvMenuMinumanAdapter?.setData(menuMinuman as List<MenuModel>)
        }

        setupRvMenu()

        this.binding?.tvNameMenu?.text = userPreference.getSession().username
        setupBtnLogout()

        setupRvMenu()
        setupBtnCheckout()

    }

    fun setupBtnLogout() {
        binding?.btnLogoutCashierMenu?.setOnClickListener {
            userPreference.deleteSession()
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }

    fun setupBtnCheckout(){
        binding?.btnCheckout?.setOnClickListener {
            (activity as MainActivity).moveToCheckout()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }


    fun setupRvMenu() {
        binding?.rvCashierMakanan?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = rvMenuMakananAdapter

        }
        binding?.rvCashierMinuman?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = rvMenuMinumanAdapter
        }
    }

    fun observeSelectedMenu() {
        MenuRepository.keranjang.observe(viewLifecycleOwner) { it ->
            Log.d("FragmentMenu", "observeSelectedMenu: ${it.size}")
            binding?.apply {
                Log.d("FragmentMenu", "btnCheckout visibility: ${btnCheckout.visibility}")
                viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
                    if (it.isNotEmpty()) {
                        btnCheckout.visibility = View.VISIBLE
                    } else {
                        btnCheckout.visibility = View.GONE
                    }
                    btnCheckout.text = "Checkout (${it.size}) Item"

                }

            }
        }
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            FragmentMenu()
    }
}