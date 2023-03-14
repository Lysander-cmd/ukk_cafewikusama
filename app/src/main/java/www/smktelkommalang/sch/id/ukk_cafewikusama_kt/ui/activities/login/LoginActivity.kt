package www.smktelkommalang.sch.id.ukk_cafewikusama_kt.ui.activities.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import www.smktelkommalang.sch.id.ukk_cafewikusama_kt.data.repository.AuthRepository
import www.smktelkommalang.sch.id.ukk_cafewikusama_kt.data.source.preferences.UserPreferences
import www.smktelkommalang.sch.id.ukk_cafewikusama_kt.databinding.ActivityLoginBinding
import www.smktelkommalang.sch.id.ukk_cafewikusama_kt.ui.activities.admin.AdminActivity
import www.smktelkommalang.sch.id.ukk_cafewikusama_kt.activities.main.MainActivity

class LoginActivity : AppCompatActivity() {
    private var binding : ActivityLoginBinding? = null
    private val userPreferences by lazy { UserPreferences(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding?.root)

//         check logged in user
        if (!(userPreferences.getSession()).token.isNullOrEmpty()){
                moveToAdminActivity()
        }
        setupInput()

    }

    fun setupInput(){

        binding?.btnLogin?.setOnClickListener{
            //DO LOGIN HERE
            if (formisvalid()){
                AuthRepository.doLogin(
                    binding?.edtEmail?.text.toString(),
                    binding?.edtPassword?.text.toString()
                ).observe(this){
                    if (it != null){
                        userPreferences.saveSession(it)
                        if (it.role == "admin"){
                            moveToAdminActivity()
                        }else{
                            moveToMainActivity()
                        }

                    }
                }
            }
        }
    }

    fun formisvalid() : Boolean{
        when{

            binding?.edtEmail?.text.isNullOrEmpty() -> {
                binding?.edtEmail!!.error = "Username tidak boleh kosong"
                return false
            }

            binding?.edtPassword?.text.isNullOrEmpty() -> {
                binding?.edtPassword!!.error = "Password tidak boleh kosong"
                return false
            }
            else -> {
                return true

            }
        }
    }

    fun moveToMainActivity(){
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun moveToAdminActivity(){
        val intent = Intent(this@LoginActivity, AdminActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}