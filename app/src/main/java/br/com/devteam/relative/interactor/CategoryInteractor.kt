package br.com.devteam.relative.interactor

import android.content.Context
import android.net.Uri
import br.com.devteam.relative.R
import br.com.devteam.relative.domain.ApiResponse
import br.com.devteam.relative.domain.Category
import br.com.devteam.relative.repository.CategoryReposotory

class CategoryInteractor(private val context: Context) {
    val repository = CategoryReposotory(context)

    val storageInteractor = StorageInteractor(context)

    fun getAll(callback: (apiResponse: ApiResponse<List<Category>>) -> Unit) {
        repository.getAll(callback)
    }

    fun getCategoryCardImageUrl(
        path: String,
        callback: (apiResponse: ApiResponse<Uri>?) -> Unit
    ) {
        storageInteractor.getFileDownloadUrl(
                "${context.resources.getString(R.string.category_card_image_ref)}/${path}",
        callback
        )
    }
}