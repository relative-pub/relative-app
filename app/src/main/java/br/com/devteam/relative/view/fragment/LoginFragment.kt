package br.com.devteam.relative.view.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.com.devteam.relative.R
import br.com.devteam.relative.databinding.LoginFragmentBinding
import br.com.devteam.relative.utils.hideKeyboard
import br.com.devteam.relative.view.activity.HostActivity
import br.com.devteam.relative.view.activity.MainActivity
import br.com.devteam.relative.view.activity.StartActivity
import br.com.devteam.relative.viewmodel.AuthViewModel
import br.com.devteam.relative.viewmodel.UserProfileViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.login_fragment.*


class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    var hostActivity: HostActivity? = null

    private lateinit var binding: LoginFragmentBinding

    private val viewModel: AuthViewModel by lazy {
        ViewModelProvider(this).get(AuthViewModel::class.java)
    }

    // Configure Google Sign In
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .build()
    var mGoogleSignInClient: GoogleSignInClient? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LoginFragmentBinding.inflate(inflater, container, false)
        binding.fragment = this@LoginFragment
        binding.userCredentials = viewModel
        binding.lifecycleOwner = this

        hostActivity = activity as HostActivity
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity().applicationContext, gso);

        isLogged()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    fun login(view: View) {
        hostActivity?.showLoading()
        viewModel.login {
            hostActivity?.hideLoading()
            if (it!!.success) {
//                viewModel.getUserProfile()
                println("email: ${viewModel.email.value}, password: ${viewModel.password.value}")

                Toast.makeText(
                    requireActivity().applicationContext,
                    "Login efetuado com sucesso.",
                    Toast.LENGTH_SHORT
                ).show()
                navToNextPage()
            } else {
                Toast.makeText(
                    requireActivity().applicationContext,
                    it.userMessage,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    fun navToNextPage() {
        if (viewModel.userProfile.value == null) {
            val startIntent =
                Intent(requireActivity().applicationContext, StartActivity::class.java)
            startActivity(startIntent)
        } else {
            val mainIntent = Intent(requireActivity().applicationContext, MainActivity::class.java)
            startActivity(mainIntent)
        }
    }

    fun isLogged() {
        if (viewModel.currentUser.value != null) {
            hostActivity?.showLoading()
            viewModel.getUserProfile {
                hostActivity?.hideLoading()
                navToNextPage()
            }
        }
    }

    fun googleSignIn(view: View) {
        val signInIntent = mGoogleSignInClient!!.signInIntent
        startActivityForResult(signInIntent, 1)
    }

    fun googleSingOut(view: View) {
        viewModel.signOut {
            if (it!!.success) {
                Toast.makeText(
                    requireActivity().applicationContext,
                    it.userMessage,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == 1) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            viewModel.loginWithGoogle(account) {
                Toast.makeText(
                    requireActivity().applicationContext,
                    "Login com Google efetuado com sucesso. " + " Usuário: " + it?.data?.displayName,
                    Toast.LENGTH_SHORT
                ).show()
                Log.println(Log.INFO, "Google SignIn", it?.data?.displayName)
            }
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Toast.makeText(
                requireActivity().applicationContext,
                "Erro ao afetuar login com Google.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun navToRedefinirSenhaFragment(view: View) {
        findNavController().navigate(R.id.action_loginFragment_to_redefinePasswordFragment)
    }

    fun navToRegisterFragment(view: View) {
        findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
    }
}
