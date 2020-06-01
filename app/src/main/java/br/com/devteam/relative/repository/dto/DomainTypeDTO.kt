package br.com.devteam.relative.repository.dto

import br.com.devteam.relative.domain.DomainType
import br.com.devteam.relative.interfaces.DTO

data class DomainTypeDTO(
    var id: Int? = null,
    var name: String? = null,
    var description: String? = null,
    var parentId: String? = null
) : DTO<DomainType> {
    override
    fun toDomain() = DomainType(
        id = id,
        name = name,
        description = description,
        parentId = parentId
    )
}