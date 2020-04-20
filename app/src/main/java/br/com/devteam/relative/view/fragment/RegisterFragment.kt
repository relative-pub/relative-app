package br.com.devteam.relative.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.com.devteam.relative.databinding.RegisterFragmentBinding
import br.com.devteam.relative.viewmodel.AuthViewModel
import br.com.devteam.relative.R

class RegisterFragment : Fragment() {

    companion object {
        fun newInstance() =
            RegisterFragment()
    }

    lateinit var binding: RegisterFragmentBinding

    private val viewModel: AuthViewModel by lazy {
        ViewModelProvider(this).get(AuthViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RegisterFragmentBinding.inflate(inflater, container, false)
        binding.fragment = this@RegisterFragment
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

    fun onSubmitForm(view: View) {

        viewModel.createUser {
            if (it!!.success) {
                println("email: ${viewModel.email.value}, password: ${viewModel.password.value}")

                Toast.makeText(
                        requireActivity().applicationContext,
                        "Usuário criado com sucesso.",
                        Toast.LENGTH_LONG
                    ).show()
                    findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            } else {
                Toast.makeText(
                    requireActivity().applicationContext,
                    it.userMessage,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}
