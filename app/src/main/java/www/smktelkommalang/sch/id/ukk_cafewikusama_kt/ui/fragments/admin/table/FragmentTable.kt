package com.readthefuckingmanual.fuckukk.ui.fragments.admin.table

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.readthefuckingmanual.fuckukk.R
import com.readthefuckingmanual.fuckukk.data.model.meja.MejaModel
import com.readthefuckingmanual.fuckukk.data.source.preferences.UserPreferences
import com.readthefuckingmanual.fuckukk.data.source.remote.datasource.MejaRemoteDataSource
import com.readthefuckingmanual.fuckukk.databinding.FragmentTableBinding
import com.readthefuckingmanual.fuckukk.ui.activities.admin.AdminActivity
import com.readthefuckingmanual.fuckukk.ui.activities.login.LoginActivity
import com.readthefuckingmanual.fuckukk.ui.fragments.cashier.menu.FragmentMenu

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