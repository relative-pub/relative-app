package br.com.devteam.relative.repository.dto

import br.com.devteam.relative.enums.ProfileVisibilityEnum
import br.com.devteam.relative.domain.UserProfile
import br.com.devteam.relative.interfaces.DTO
import br.com.devteam.relative.utils.listDTOToDomainList

data class UserProfileDTO(
    var uid: String? = null,
    var userName: String? = null,
    var email: String? = null,
    var displayName: String? = null,
    var photoUrl: String? = null,
    var bio: String? = null,
    var birthDate: String? = null,
    var socialMedia: List<SocialMediaDTO>? = null,
    var categories: List<CategoryDTO>? = null,
    var visibilityEnum: ProfileVisibilityEnum? = null
) : DTO<UserProfile> {
    override fun toDomain() =
        UserProfile(
            uid = uid,
            userName = userName,
            email = email,
            displayName = displayName,
            photoUrl = photoUrl,
            bio = bio,
            birthDate = birthDate,
            socialMedia = listDTOToDomainList(socialMedia),
            categories = listDTOToDomainList(categories),
            visibilityEnum = visibilityEnum
        )
}
