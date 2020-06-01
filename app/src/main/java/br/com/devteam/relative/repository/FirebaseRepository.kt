package br.com.devteam.relative.repository

import android.content.Context
import br.com.devteam.relative.domain.ApiResponse
import br.com.devteam.relative.interfaces.DTO
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

open abstract class FirebaseRepository<T, E>(
    private val context: Context,
    val collectionPath: String
) {

    val firestore = FirebaseFirestore.getInstance()

    abstract fun getJavaClass(): Class<T>

    fun collectioRef(): CollectionReference {
        return firestore.collection(collectionPath)
    }

    fun documentRef(path: String): DocumentReference {
        val collection = collectioRef()
        return collection.document(path)
    }

    fun saveDocument(
        path: String,
        document: E,
        callback: (apiResponse: ApiResponse<Unit>?) -> Unit
    ) {
        val ref = documentRef(path)
        ref.set(document!!).addOnSuccessListener {
            callback(ApiResponse<Unit>())
        }.addOnFailureListener {
            callback(
                ApiResponse<Unit>(
                    false,
                    userMessage = "Houve um problema ao salvar o documento."
                )
            )
        }
    }


    fun getDocument(path: String, callback: (apiResponse: ApiResponse<E>) -> Unit) {
        val ref = documentRef(path!!)
        ref.get().addOnSuccessListener {
            val documentDTO = it.toObject(getJavaClass()) as DTO<E>
            if (documentDTO != null) {
                callback(ApiResponse<E>(data = documentDTO.toDomain()))
            } else {
                callback(
                    ApiResponse<E>(
                        success = false,
                        userMessage = "Documento não encontrado.",
                        data = null
                    )
                )
            }
        }.addOnFailureListener {
            callback(
                ApiResponse<E>(
                    success = false,
                    userMessage = "Documento não encontrado.",
                    data = null
                )
            )
        }
    }

    fun getAll(callback: (apiResponse: ApiResponse<List<E>>) -> Unit) {
        val collection = collectioRef()

        collection.get().addOnSuccessListener { querySnapshot ->
            var listDocument = ArrayList<E>()
            querySnapshot.forEach {
                if (it != null) {
                    listDocument.add((it.toObject(getJavaClass()) as DTO<E>).toDomain())
                }
            }
            callback(ApiResponse<List<E>>(data = listDocument))
        }.addOnFailureListener {
            callback(
                ApiResponse<List<E>>(
                    success = false,
                    userMessage = "Documentos não encontrados.",
                    data = null
                )
            )
        }
    }
}