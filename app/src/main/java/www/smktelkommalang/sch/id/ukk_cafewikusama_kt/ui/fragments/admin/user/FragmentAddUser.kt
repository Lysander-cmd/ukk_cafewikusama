package www.smktelkommalang.sch.id.ukk_cafewikusama_kt.ui.fragments.admin.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import www.smktelkommalang.sch.id.ukk_cafewikusama_kt.R

class FragmentAddUser : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_user, container, false)
    }

    companion object {
        fun newInstance() =
            FragmentAddUser()
    }
}