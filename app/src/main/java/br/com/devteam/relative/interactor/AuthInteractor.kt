package br.com.devteam.relative.interactor

import br.com.devteam.relative.domain.ApiResponse
import br.com.devteam.relative.repository.AuthRepository

class AuthInteractor() {
    private val authRepository = AuthRepository()

    fun login(email: String?, password: String?, callback: (apiResponse: ApiResponse?) -> Unit) {
        if (email == null || password == null) {
            callback(
                ApiResponse(
                    false,
                    userMessage = "Preencha os campos de usuário e senha."
                )
            )
        } else {
            authRepository.login(email, password, callback)
        }
    }

    fun createUser(
        email: String?,
        password: String?,
        confirmPassword: String?,
        callback: (apiResponse: ApiResponse?) -> Unit
    ) {

        if (
            email == null ||
            password == null ||
            confirmPassword == null
        ) {
            callback(ApiResponse(false, userMessage = "É necessário preencher os campos."))
        } else if (password.length < 6) {
            callback(ApiResponse(false, userMessage = "A senha precisa ter seis ou mais caracteres."))
        } else if (confirmPassword != password) {
            callback(ApiResponse(false, userMessage = "As senhas precisam ser iguais."))
        } else {
            authRepository.createUser(email, password, callback)
        }
    }

    fun sendPasswordResetEmail(email: String?, callback: (apiResponse: ApiResponse?) -> Unit) {
        if (email != null) {
            authRepository.sendPasswordResetEmail(email, callback)
        } else {
            callback(ApiResponse(false, userMessage = "Preencha o campo e-mail."))
        }
    }
}