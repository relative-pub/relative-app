package br.com.devteam.relative.interactor

import android.content.Context
import android.net.Uri
import br.com.devteam.relative.domain.ApiResponse
import br.com.devteam.relative.repository.StorageRepository

class StorageInteractor(private val context: Context) {

    val storageRepository = StorageRepository(context)


    fun uploadFile(
        localFilePath: Uri,
        path: String,
        callback: (apiResponse: ApiResponse<Unit>?) -> Unit
    ) {
        // TODO: Validate params
        storageRepository.uploadFile(
            localFilePath,
            path,
            callback
        )
    }

    fun getFileDownloadUrl(
        path: String,
        callback: (apiResponse: ApiResponse<Uri>?) -> Unit
    ) {
        storageRepository.getFileDownloadUrl(path, callback)
    }
}