package br.com.devteam.relative.domain

import br.com.devteam.relative.enums.ProfileVisibilityEnum

data class UserProfile(
    var uid: String? = null,
    var userName: String? = null,
    var email: String? = null,
    var displayName: String? = null,
    var photoUrl: String? = null,
    var bio: String? = null,
    var birthDate: String? = null,
    var socialMedia: List<SocialMedia>? = null,
    var categories: List<Category>? = null,
    var visibilityEnum: ProfileVisibilityEnum? = null
)
