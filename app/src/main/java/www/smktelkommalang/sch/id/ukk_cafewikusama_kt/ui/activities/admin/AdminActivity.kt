package www.smktelkommalang.sch.id.ukk_cafewikusama_kt.ui.activities.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import www.smktelkommalang.sch.id.ukk_cafewikusama_kt.R
import www.smktelkommalang.sch.id.ukk_cafewikusama_kt.data.repository.MenuRepository
import www.smktelkommalang.sch.id.ukk_cafewikusama_kt.data.source.preferences.UserPreferences
import www.smktelkommalang.sch.id.ukk_cafewikusama_kt.databinding.ActivityAdminBinding
import www.smktelkommalang.sch.id.ukk_cafewikusama_kt.fragments.admin.menu.FragmentAdminMenu
import www.smktelkommalang.sch.id.ukk_cafewikusama_kt.fragments.admin.menu.FragmentCrudMenu
import www.smktelkommalang.sch.id.ukk_cafewikusama_kt.fragments.admin.table.FragmentCrudTable
import www.smktelkommalang.sch.id.ukk_cafewikusama_kt.fragments.admin.table.FragmentTable
import www.smktelkommalang.sch.id.ukk_cafewikusama_kt.fragments.admin.user.FragmentUser

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