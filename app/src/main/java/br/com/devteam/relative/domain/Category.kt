package br.com.devteam.relative.domain

import br.com.devteam.relative.interfaces.Domain
import br.com.devteam.relative.repository.dto.CategoryDTO

data class Category(
    var id: String? = null,
    var name: String? = null,
    var description: String? = null,
    var tag: List<String>? = null,
    var card_image: String? = null
) : Domain<CategoryDTO> {
    override fun toDTO() = CategoryDTO(
        id = id,
        name = name,
        description = description,
        tag = tag,
        card_image = card_image
    )
}