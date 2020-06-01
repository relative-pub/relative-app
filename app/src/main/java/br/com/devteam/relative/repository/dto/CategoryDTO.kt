package br.com.devteam.relative.repository.dto

import br.com.devteam.relative.domain.Category
import br.com.devteam.relative.interfaces.DTO

data class CategoryDTO(
    var id: String? = null,
    var name: String? = null,
    var description: String? = null,
    var tag: List<String>? = null,
    var card_image: String? = null
) : DTO<Category> {
    override
    fun toDomain() = Category(
        id = id,
        name = name,
        description = description,
        tag = tag,
        card_image = card_image
    )
}