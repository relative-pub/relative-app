package br.com.devteam.relative.repository

import com.google.firebase.auth.FirebaseAuth
import br.com.devteam.relative.domain.ApiResponse

class AuthRepository() {

    private lateinit var auth: FirebaseAuth

    companion object {

        private var instance: AuthRepository? = null

        fun getInstance(): AuthRepository {
            if (instance == null) {
                instance = AuthRepository()
            }
            return instance!!
        }
    }


    init {
        auth = FirebaseAuth.getInstance()
    }

    fun login(email: String, password: String, callback: (apiResponse: ApiResponse?) -> Unit) {
        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {

            callback(ApiResponse())
        }.addOnFailureListener {
            callback(
                ApiResponse(
                    false,
                    userMessage = "Usuário ou senha incorretos."
                )
            )
        }
    }

    fun createUser(email: String, password: String, callback: (apiResponse: ApiResponse?) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                callback(ApiResponse())
            }
            .addOnFailureListener {
                callback(
                    ApiResponse(
                        false,
                        userMessage = "Usuário ou senha incorretos."
                    )
                )
            }
    }

    fun sendPasswordResetEmail(email: String, callback: (apiResponse: ApiResponse?) -> Unit) {
        auth.sendPasswordResetEmail(email).addOnCompleteListener {
            callback(ApiResponse())
        }.addOnFailureListener {
            callback(
                ApiResponse(
                    false,
                    userMessage = "Erro ao enviar o email."
                )
            )
        }
    }
}