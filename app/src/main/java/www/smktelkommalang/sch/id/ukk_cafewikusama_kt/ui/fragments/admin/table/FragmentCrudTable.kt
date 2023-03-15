package www.smktelkommalang.sch.id.ukk_cafewikusama_kt.ui.fragments.admin.table

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import www.smktelkommalang.sch.id.ukk_cafewikusama_kt.R
import www.smktelkommalang.sch.id.ukk_cafewikusama_kt.data.source.preferences.UserPreferences
import www.smktelkommalang.sch.id.ukk_cafewikusama_kt.data.source.remote.datasource.MejaRemoteDataSource
import www.smktelkommalang.sch.id.ukk_cafewikusama_kt.databinding.FragmentCrudTableBinding
import www.smktelkommalang.sch.id.ukk_cafewikusama_kt.databinding.FragmentMenuBinding
import www.smktelkommalang.sch.id.ukk_cafewikusama_kt.ui.activities.admin.AdminActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FragmentCrudTable : Fragment() {

    private var binding: FragmentCrudTableBinding? = null

    private val userPreference by lazy {
        UserPreferences(requireContext())
    }

    private val userToken by lazy {
        userPreference.getSession().token
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeAddMeja()
    }

    private fun observeAddMeja() {
//        val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJyb3dzIjpbeyJpZF91c2VyIjoxNywiZW1haWwiOiJpbmZlcm5vNEBnbWFpbC5jb20iLCJuYW1hX3VzZXIiOiJJbmZlcm5vIiwicm9sZSI6ImFkbWluIiwidXNlcm5hbWUiOiJJbmZlcm5vIiwicGFzc3dvcmQiOiI1N2M5YjdiY2M3NTI0NDljNTM3ZGUxMTE5YjA2NjU3MSJ9XSwiaWF0IjoxNjc4ODEyMjk5LCJleHAiOjE2ODAyNTIyOTl9.ZR2Moj9Yj3IVovGwY1SN2r4KraGCUbPRmRNl-rBoIZc"
        binding?.apply {
            btnAddMeja.setOnClickListener {
                if (editNamaMeja.text.isNotEmpty()){
                    MejaRemoteDataSource.apply {
                        addMeja(userToken!!, editNamaMeja.text.toString())
                        mejaResponse.observe(viewLifecycleOwner){
                            if(it?.status == "Success"){
                                viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
                                    (activity as AdminActivity).moveToAdminTableFragment()

                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCrudTableBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            FragmentCrudTable()

    }

}