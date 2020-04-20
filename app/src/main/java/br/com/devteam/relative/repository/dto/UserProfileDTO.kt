package br.com.devteam.relative.repository.dto

import br.com.devteam.relative.domain.UserProfile

data class UserProfileDTO(
    var token: String? = null,
    var displayName: String? = null,
    var email: String? = null,
    var href: String? = null,
    var id: String? = null,
    var type: String? = null,
    var nickmane: String? = null
) : DTO<UserProfile> {
    override fun toDomain() =
        UserProfile(
            token = token,
            displayName = displayName,
            email = email,
            href = href,
            id = id,
            type = type,
            nickmane = nickmane
        )
}
