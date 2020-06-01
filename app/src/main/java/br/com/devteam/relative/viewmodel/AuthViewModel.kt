package br.com.devteam.relative.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import br.com.devteam.relative.domain.ApiResponse
import br.com.devteam.relative.domain.UserProfile
import br.com.devteam.relative.interactor.AuthInteractor
import br.com.devteam.relative.interactor.UserProfileInteractor
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser

class AuthViewModel(val app: Application) : AndroidViewModel(app) {

    private val authInteractor = AuthInteractor()
    //TODO: Corrigir falha na arquitetura abaixo
    private val userProfileInteractor = UserProfileInteractor(app.applicationContext)

    val email: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()
    val confirmPassword: MutableLiveData<String> = MutableLiveData()
    val currentUser: MutableLiveData<FirebaseUser> = MutableLiveData()
    //TODO: Corrigir falha na arquitetura abaixo
    val userProfile: MutableLiveData<UserProfile> = MutableLiveData()

    init {
        getCurrentUser()
    }

    fun login(callback: (apiResponse: ApiResponse<Unit>?) -> Unit) {
        authInteractor.login(email.value, password.value) { response ->
            getUserProfile {
                callback(response)
            }
        }
    }

    fun signOut(callback: (apiResponse: ApiResponse<Unit>?) -> Unit) {
        authInteractor.signOut(callback)
    }

    fun getCurrentUser (){
        authInteractor.getCurrentUser {
            if(it!!.success) {
                currentUser.value = it.data
            }
        }
    }

    fun loginWithGoogle(account: GoogleSignInAccount?, callback: (apiResponse: ApiResponse<FirebaseUser>?) -> Unit){
        authInteractor.loginWithGoogle(account, callback)
    }

    fun sendPasswordResetEmail(callback: (apiResponse: ApiResponse<Unit>?) -> Unit) {
        authInteractor.sendPasswordResetEmail(email.value, callback)
    }

    fun createUser(callback: (apiResponse: ApiResponse<Unit>?) -> Unit) {
        authInteractor.createUser(email.value, password.value, confirmPassword.value, callback)
    }
    //TODO: Corrigir falha na arquitetura abaixo
    fun getUserProfile(callback: (apiResponse: ApiResponse<UserProfile>?) -> Unit) {
        userProfileInteractor.getUserProfile {
            userProfile.value = it.data
            callback(ApiResponse<UserProfile>(data = it.data))
        }
    }

}
