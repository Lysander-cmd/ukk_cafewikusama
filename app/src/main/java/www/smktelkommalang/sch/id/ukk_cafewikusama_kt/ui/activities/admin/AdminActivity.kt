package com.readthefuckingmanual.fuckukk.ui.activities.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.readthefuckingmanual.fuckukk.R
import com.readthefuckingmanual.fuckukk.data.repository.MenuRepository
import com.readthefuckingmanual.fuckukk.data.source.preferences.UserPreferences
import com.readthefuckingmanual.fuckukk.databinding.ActivityAdminBinding
import com.readthefuckingmanual.fuckukk.ui.fragments.admin.menu.FragmentAdminMenu
import com.readthefuckingmanual.fuckukk.ui.fragments.admin.menu.FragmentCrudMenu
import com.readthefuckingmanual.fuckukk.ui.fragments.admin.table.FragmentCrudTable
import com.readthefuckingmanual.fuckukk.ui.fragments.admin.table.FragmentTable
import com.readthefuckingmanual.fuckukk.ui.fragments.admin.user.FragmentUser

class AdminActivity : AppCompatActivity() {

    private var binding : ActivityAdminBinding? = null
    private val userFragment = FragmentUser.newInstance()
    private val tableFragment = FragmentTable.newInstance()
    private val menuFragment = FragmentAdminMenu.newInstance()
    private val fragmentCrudMenu = FragmentCrudMenu.newInstance()
    private val fragmentCrudTable = FragmentCrudTable.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setupNavigation()
        binding?.bottomNavigationAdmin?.visibility = View.VISIBLE
        observeNavigation()
        moveToAdminTableFragment()

    }


    fun setupNavigation(){
        binding?.apply {
            bottomNavigationAdmin?.setOnItemSelectedListener { user ->
                when (user.itemId) {
                    R.id.nav_menu_admin -> {
                        changeFragment(menuFragment)

//                    binding?.tvJudul?.text = "Jadwal Piala Dunia"
                        return@setOnItemSelectedListener true
                    }
                    R.id.nav_table -> {
                        changeFragment(tableFragment)
//                    binding?.tvJudul?.text = "Hasil Piala Dunia"
                        return@setOnItemSelectedListener true
                    }
                    R.id.nav_user -> {
                        changeFragment(userFragment)

                        return@setOnItemSelectedListener true
                    }
                }
                false
            }
            changeFragment(userFragment)
        }
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .replace(R.id.adminRootView, fragment)
            .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    fun moveToAdminTableFragment() {
        changeFragment(tableFragment)
    }

    fun moveToAdminMenuFragment() {
        changeFragment(menuFragment)
    }

    fun moveToCrudMenuFragment() {

        changeFragment(fragmentCrudMenu)
    }

    fun moveToCrudTableFragment() {
        changeFragment(fragmentCrudTable)
    }

    override fun onBackPressed() {
//        MenuRepository.selectedmenu.observe(view)
        moveToAdminTableFragment()
//        super.onBackPressed()
    }

    fun observeNavigation(){
        supportFragmentManager.addFragmentOnAttachListener { fragmentManager, fragment ->
            if (fragment is FragmentCrudTable || fragment is FragmentCrudMenu){
                binding?.bottomNavigationAdmin?.visibility = View.GONE
            }else{
                binding?.bottomNavigationAdmin?.visibility = View.VISIBLE
            }
        }
    }

}