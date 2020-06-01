package br.com.devteam.relative.repository

import android.content.Context
import br.com.devteam.relative.R
import br.com.devteam.relative.repository.dto.CategoryDTO
import br.com.devteam.relative.domain.Category

class CategoryReposotory(val context: Context) : FirebaseRepository<CategoryDTO, Category>(
    context,
    context.resources.getString(R.string.category_ref)
) {
    override
    fun getJavaClass(): Class<CategoryDTO> {
        return CategoryDTO::class.java
    }
}