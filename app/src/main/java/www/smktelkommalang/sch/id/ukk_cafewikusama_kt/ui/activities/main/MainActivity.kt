package www.smktelkommalang.sch.id.ukk_cafewikusama_kt.activities.main

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_CLOSE
import androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN
import androidx.lifecycle.lifecycleScope
import www.smktelkommalang.sch.id.ukk_cafewikusama_kt.R
import www.smktelkommalang.sch.id.ukk_cafewikusama_kt.data.repository.MejaRepository
import www.smktelkommalang.sch.id.ukk_cafewikusama_kt.data.repository.MenuRepository
import www.smktelkommalang.sch.id.ukk_cafewikusama_kt.data.source.preferences.UserPreferences
import www.smktelkommalang.sch.id.ukk_cafewikusama_kt.data.source.remote.datasource.MejaRemoteDataSource
import www.smktelkommalang.sch.id.ukk_cafewikusama_kt.databinding.ActivityMainBinding
import www.smktelkommalang.sch.id.ukk_cafewikusama_kt.ui.fragments.cashier.history.FragmentHistory
import www.smktelkommalang.sch.id.ukk_cafewikusama_kt.ui.fragments.cashier.menu.FragmentMenu
import www.smktelkommalang.sch.id.ukk_cafewikusama_kt.fragments.cashier.transaksi.FragmentTransaksi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private var binding : ActivityMainBinding? = null
    private val userPreferences by lazy { UserPreferences(this) }
    private val userToken by lazy { userPreferences.getSession().token }
    private val menuFragment = FragmentMenu.newInstance()
    private val historyFragment = FragmentHistory.newInstance()
    private val transaksiFragment = FragmentTransaksi.newInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding?.root)

        setupNavigation()
        MejaRemoteDataSource.getListMeja(userToken!!)

    }


//    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
//        observeSelectedMenu()
//        return super.onCreateView(name, context, attrs)
//    }



    fun setupNavigation(){
        binding?.apply {
            bottomNavigation?.setOnItemSelectedListener { menu ->
                when (menu.itemId) {
                    R.id.nav_menu -> {
                        changeFragment(menuFragment)

//                    binding?.tvJudul?.text = "Jadwal Piala Dunia"
                        return@setOnItemSelectedListener true
                    }
                    R.id.nav_history -> {
                        changeFragment(historyFragment)
//                    binding?.tvJudul?.text = "Hasil Piala Dunia"
                        return@setOnItemSelectedListener true
                    }
                }
                false
            }
            changeFragment(menuFragment)

        }
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .setTransition(TRANSIT_FRAGMENT_CLOSE)
            .setTransition(TRANSIT_FRAGMENT_OPEN)
            .replace(R.id.homeRootView, fragment)
            .commit()
    }

    fun moveToCheckout(){
        changeFragment(transaksiFragment)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    fun moveToMenuFragment() {
        changeFragment(menuFragment)
    }

    fun moveToHistory() {
        changeFragment(historyFragment)
    }
}