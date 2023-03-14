package www.smktelkommalang.sch.id.ukk_cafewikusama_kt.ui.fragments.admin.menu

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
import www.smktelkommalang.sch.id.ukk_cafewikusama_kt.R
import www.smktelkommalang.sch.id.ukk_cafewikusama_kt.data.model.menu.MenuModel
import www.smktelkommalang.sch.id.ukk_cafewikusama_kt.data.repository.MenuRepository
import www.smktelkommalang.sch.id.ukk_cafewikusama_kt.data.source.preferences.UserPreferences
import www.smktelkommalang.sch.id.ukk_cafewikusama_kt.databinding.FragmentAdminMenuBinding
import www.smktelkommalang.sch.id.ukk_cafewikusama_kt.ui.activities.admin.AdminActivity
import www.smktelkommalang.sch.id.ukk_cafewikusama_kt.ui.activities.login.LoginActivity
import www.smktelkommalang.sch.id.ukk_cafewikusama_kt.ui.activities.main.MainActivity
import www.smktelkommalang.sch.id.ukk_cafewikusama_kt.ui.fragments.cashier.menu.FragmentMenu
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