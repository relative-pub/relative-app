package br.com.devteam.relative.repository

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import br.com.devteam.relative.domain.ApiResponse
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File


class StorageRepository(val context: Context) {
    val storage = FirebaseStorage.getInstance()

    private fun getStorageRef(path: String): StorageReference {
        return storage.getReference(path)
    }

    fun uploadFile(
        localFilePath: Uri,
        path: String,
        callback: (apiResponse: ApiResponse<Unit>?) -> Unit
    ) {
        val ref = getStorageRef(path)
        val uploadTask = ref.putFile(localFilePath)
        uploadTask.addOnSuccessListener {
            callback(ApiResponse<Unit>())
        }.addOnFailureListener {
            callback(
                ApiResponse<Unit>(
                    false,
                    userMessage = "Houve um problema ao salvar a imagem de perfil."
                )
            )
        }
    }

    fun getFileDownloadUrl(
        path: String,
        callback: (apiResponse: ApiResponse<Uri>?) -> Unit
    ) {
        val ref = getStorageRef(path)
        ref.downloadUrl
            .addOnSuccessListener {
                callback(
                    ApiResponse<Uri>(
                        data = it
                    )
                )
            }.addOnFailureListener {
                callback(
                    ApiResponse<Uri>(
                        false,
                        userMessage = "Houve um problema ao buscar a uri do arquivo."
                    )
                )
            }
    }
}