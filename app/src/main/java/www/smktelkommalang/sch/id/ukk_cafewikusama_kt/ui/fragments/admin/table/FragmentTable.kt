package www.smktelkommalang.sch.id.ukk_cafewikusama_kt.ui.fragments.admin.table

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import www.smktelkommalang.sch.id.ukk_cafewikusama_kt.R
import www.smktelkommalang.sch.id.ukk_cafewikusama_kt.data.model.meja.MejaModel
import www.smktelkommalang.sch.id.ukk_cafewikusama_kt.data.source.preferences.UserPreferences
import www.smktelkommalang.sch.id.ukk_cafewikusama_kt.data.source.remote.datasource.MejaRemoteDataSource
import www.smktelkommalang.sch.id.ukk_cafewikusama_kt.databinding.FragmentTableBinding
import www.smktelkommalang.sch.id.ukk_cafewikusama_kt.ui.activities.admin.AdminActivity
import www.smktelkommalang.sch.id.ukk_cafewikusama_kt.ui.activities.login.LoginActivity
import www.smktelkommalang.sch.id.ukk_cafewikusama_kt.ui.fragments.cashier.menu.FragmentMenu

class FragmentTable : Fragment() {

    private var binding: FragmentTableBinding? = null
    private var rvAdminTableAdapter: ListTableAdapter? = null

    private val userPreference by lazy {
        UserPreferences(requireContext())
    }

    private val userToken by lazy {
        userPreference.getSession().token
    }

    private var AdminTableAdapter: List<MejaModel?>? = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rvAdminTableAdapter = ListTableAdapter()
        MejaRemoteDataSource.getListMeja(userToken!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTableBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        MejaRemoteDataSource.mejaList.observe(viewLifecycleOwner){
            if(it != null){
                rvAdminTableAdapter?.setData(it.values as List<MejaModel>)
            }
        }
        setupRvMeja()
        setupBtnLogout()
        setupFAB()
    }

    fun setupFAB() {
        binding?.fabAddMejaAdmin?.setOnClickListener{
            (activity as AdminActivity).moveToCrudTableFragment()
        }
    }

    fun setupBtnLogout() {
        binding?.btnLogoutMejaAdmin?.setOnClickListener {
            userPreference.deleteSession()
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }

    fun setupRvMeja() {
        binding?.rvMejaAdmin?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = rvAdminTableAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            FragmentTable()
        }



}