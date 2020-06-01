package br.com.devteam.relative.repository.dto

import br.com.devteam.relative.domain.SocialMedia
import br.com.devteam.relative.interfaces.DTO

data class SocialMediaDTO(
    var link: String,
    val type: DomainTypeDTO
) : DTO<SocialMedia> {
    override
    fun toDomain() = SocialMedia(
        link = link,
        type = type.toDomain()
    )
}