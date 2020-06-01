package br.com.devteam.relative.interactor

import br.com.devteam.relative.domain.ApiResponse
import br.com.devteam.relative.repository.AuthRepository
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser

class AuthInteractor() {
    private val authRepository = AuthRepository()

    fun login(email: String?, password: String?, callback: (apiResponse: ApiResponse<Unit>?) -> Unit) {
        if (email == null || password == null) {
            callback(
                ApiResponse<Unit>(
                    false,
                    userMessage = "Preencha os campos de usuário e senha."
                )
            )
        } else {
            authRepository.login(email, password, callback)
        }
    }

    fun loginWithGoogle (account: GoogleSignInAccount?, callback: (apiResponse: ApiResponse<FirebaseUser>?) -> Unit) {
        if(account != null) {
            authRepository.loginWithGoogle(account, callback)
        }else {
            callback(
                ApiResponse<FirebaseUser>(
                    success = false,
                    userMessage = "Usuário Google nulo."
                )
            )
        }
    }

    fun signOut(callback: (apiResponse: ApiResponse<Unit>?) -> Unit) {
        authRepository.signOut(callback)
    }

    fun getCurrentUser( callback: (apiResponse: ApiResponse<FirebaseUser>?) -> Unit) {
        authRepository.getCurrentUser(callback)
    }

    fun createUser(
        email: String?,
        password: String?,
        confirmPassword: String?,
        callback: (apiResponse: ApiResponse<Unit>?) -> Unit
    ) {

        if (
            email == null ||
            password == null ||
            confirmPassword == null
        ) {
            callback(ApiResponse<Unit>(false, userMessage = "É necessário preencher os campos."))
        } else if (password.length < 6) {
            callback(ApiResponse<Unit>(false, userMessage = "A senha precisa ter seis ou mais caracteres."))
        } else if (confirmPassword != password) {
            callback(ApiResponse<Unit>(false, userMessage = "As senhas precisam ser iguais."))
        } else {
            authRepository.createUser(email, password, callback)
        }
    }

    fun sendPasswordResetEmail(email: String?, callback: (apiResponse: ApiResponse<Unit>?) -> Unit) {
        if (email != null) {
            authRepository.sendPasswordResetEmail(email, callback)
        } else {
            callback(ApiResponse<Unit>(false, userMessage = "Preencha o campo e-mail."))
        }
    }
}