package br.com.devteam.relative.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import br.com.devteam.relative.domain.ApiResponse
import br.com.devteam.relative.interactor.AuthInteractor

class AuthViewModel(val app: Application) : AndroidViewModel(app) {

    private val authInteractor = AuthInteractor()

    val email: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()
    val confirmPassword: MutableLiveData<String> = MutableLiveData()

    fun login(callback: (apiResponse: ApiResponse?) -> Unit) {
        authInteractor.login(email.value, password.value, callback)
    }

    fun sendPasswordResetEmail(callback: (apiResponse: ApiResponse?) -> Unit) {
        authInteractor.sendPasswordResetEmail(email.value, callback)
    }

    fun createUser(callback: (apiResponse: ApiResponse?) -> Unit) {
        authInteractor.createUser(email.value, password.value, confirmPassword.value, callback)
    }

}
