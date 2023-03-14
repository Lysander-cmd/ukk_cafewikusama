package www.smktelkommalang.sch.id.ukk_cafewikusama_kt.ui.fragments.admin.user

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import www.smktelkommalang.sch.id.ukk_cafewikusama_kt.R
import www.smktelkommalang.sch.id.ukk_cafewikusama_kt.data.model.user.UserAdminModel
import www.smktelkommalang.sch.id.ukk_cafewikusama_kt.data.repository.UserRepository
import www.smktelkommalang.sch.id.ukk_cafewikusama_kt.data.source.preferences.UserPreferences
import www.smktelkommalang.sch.id.ukk_cafewikusama_kt.databinding.FragmentUserBinding
import www.smktelkommalang.sch.id.ukk_cafewikusama_kt.activities.login.LoginActivity
import www.smktelkommalang.sch.id.ukk_cafewikusama_kt.fragments.cashier.history.ListHistoryAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FragmentUser : Fragment() {

    private var binding : FragmentUserBinding? = null

    private var rvUserAdapter: ListUserAdapter? = null

    private val userPreference by lazy {
        UserPreferences(requireContext())
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rvUserAdapter = ListUserAdapter{::observeSelectedUser }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUserBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        observeSelectedUser()
        UserRepository.getAllUser().observe(viewLifecycleOwner){
            if (it != null){
                rvUserAdapter?.setData(it.values as List<UserAdminModel>)
            }

        }
        setupRvUser()
        setupBtnLogout()
    }

    private fun setupBtnLogout() {
        binding?.btnLogoutUserAdmin?.setOnClickListener {
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

    fun setupRvUser() {
        binding?.rvUserAdmin?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = rvUserAdapter
        }
    }

    fun observeSelectedUser(userModel : UserAdminModel){
        UserRepository.selecteduser.postValue(userModel)


    }
    companion object {
        @JvmStatic
        fun newInstance() =
            FragmentUser()
    }

}