package br.com.devteam.relative.repository

import com.google.firebase.auth.FirebaseAuth
import br.com.devteam.relative.domain.ApiResponse
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.protobuf.Api

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

    fun login(email: String, password: String, callback: (apiResponse: ApiResponse<Unit>?) -> Unit) {
        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {

            callback(ApiResponse<Unit>())
        }.addOnFailureListener {
            callback(
                ApiResponse<Unit>(
                    false,
                    userMessage = "Usuário ou senha incorretos."
                )
            )
        }
    }

    fun signOut(callback: (apiResponse: ApiResponse<Unit>?) -> Unit) {
        auth.signOut()
        callback(
            ApiResponse<Unit>(
            userMessage = "Usuário deslogado."
        ))
    }

    fun createUser(email: String, password: String, callback: (apiResponse: ApiResponse<Unit>?) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                callback(ApiResponse<Unit>())
            }
            .addOnFailureListener {
                callback(
                    ApiResponse<Unit>(
                        false,
                        userMessage = "Usuário ou senha incorretos."
                    )
                )
            }
    }

    fun getCurrentUser(callback: (apiResponse: ApiResponse<FirebaseUser>?) -> Unit) {
        val currentUser = auth.currentUser
        callback(
            ApiResponse<FirebaseUser>(
                userMessage = currentUser.toString(),
                data = currentUser
            )
        )
    }

    fun loginWithGoogle (account: GoogleSignInAccount, callback: (apiResponse: ApiResponse<FirebaseUser>?) -> Unit) {
        val credential = GoogleAuthProvider.getCredential(account.id, null)
        auth.signInWithCredential(credential)
            .addOnSuccessListener {
                if(auth.currentUser != null){
                    callback (
                        ApiResponse<FirebaseUser>(
                            userMessage = "" + auth.currentUser?.displayName,
                            data = auth.currentUser
                        )
                    )
                }else {
                    callback (
                        ApiResponse<FirebaseUser>(
                            success = false,
                            userMessage = "Erro ao logar com o Google."
                        )
                    )
                }
            }
            .addOnFailureListener {
                callback (
                    ApiResponse<FirebaseUser>(
                        success = false,
                        userMessage = "Erro ao logar com o Google."
                    )
                )
            }
    }

    fun sendPasswordResetEmail(email: String, callback: (apiResponse: ApiResponse<Unit>?) -> Unit) {
        auth.sendPasswordResetEmail(email).addOnCompleteListener {
            callback(ApiResponse<Unit>())
        }.addOnFailureListener {
            callback(
                ApiResponse<Unit>(
                    false,
                    userMessage = "Erro ao enviar o email."
                )
            )
        }
    }
}