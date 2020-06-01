package br.com.devteam.relative.interactor

import android.content.Context
import android.net.Uri
import br.com.devteam.relative.R
import br.com.devteam.relative.domain.ApiResponse
import br.com.devteam.relative.domain.UserProfile
import br.com.devteam.relative.repository.StorageRepository
import br.com.devteam.relative.repository.UserProfileRepository

class UserProfileInteractor(private val context: Context) {
    val repository = UserProfileRepository(context)

    val storageInteractor = StorageInteractor(context)
    val authInteractor = AuthInteractor()

    fun save(userProfile: UserProfile?, callback: (apiResponse: ApiResponse<Unit>?) -> Unit) {
        if (userProfile != null) {
            repository.saveDocument(userProfile.uid!!, userProfile, callback)
        }
    }

    fun getUserProfile(callback: (apiResponse: ApiResponse<UserProfile>) -> Unit) {
        authInteractor.getCurrentUser {
            if (it!!.success) {
                repository.getDocument(it.data!!.uid, callback)
            }
        }
    }

    fun uploadUserPrpofileImage(
        localFilePath: Uri,
        callback: (apiResponse: ApiResponse<Unit>?) -> Unit
    ) {
        //TODO: Validar parâmetros
        authInteractor.getCurrentUser {

            if (it!!.success) {
                storageInteractor.uploadFile(
                    localFilePath,
                    "${context.resources.getString(R.string.user_profile_image_ref)}/${it.data!!.uid}",
                    callback
                )
            }
        }
    }

    fun getUserProfileImageUrl(
        callback: (apiResponse: ApiResponse<Uri>?) -> Unit
    ) {
        authInteractor.getCurrentUser {
            if (it!!.success) {
                if (it.data!!.uid != null) {
                    storageInteractor.getFileDownloadUrl(
                        "${context.resources.getString(R.string.user_profile_image_ref)}/${it.data!!.uid}",
                        callback
                    )
                }
            }
        }
    }


}