package br.com.devteam.relative.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.com.devteam.relative.R
import br.com.devteam.relative.databinding.LoginFragmentBinding
import br.com.devteam.relative.viewmodel.AuthViewModel
import br.com.devteam.relative.viewmodel.UserProfileViewModel

class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var binding: LoginFragmentBinding

    private val viewModel: AuthViewModel by lazy {
        ViewModelProvider(this).get(AuthViewModel::class.java)
    }

    private val userProfileViewModel: UserProfileViewModel by lazy {
        ViewModelProvider(this).get(UserProfileViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LoginFragmentBinding.inflate(inflater, container, false)
        binding.fragment = this@LoginFragment
        binding.userCredentials = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    fun login(view: View) {
        viewModel.login {
            if (it!!.success) {
                println("email: ${viewModel.email.value}, password: ${viewModel.password.value}")

                Toast.makeText(
                    requireActivity().applicationContext,
                    "Login efetuado com sucesso.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    requireActivity().applicationContext,
                    it.userMessage,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    fun navToRedefinirSenhaFragment(view: View) {
        findNavController().navigate(R.id.action_loginFragment_to_redefinePasswordFragment)
    }

    fun navToRegisterFragment(view: View) {
        findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
    }
}
