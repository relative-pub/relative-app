package br.com.devteam.relative.domain

data class UserProfile(
    var token: String? = null,
    var displayName: String? = null,
    var email: String? = null,
    var href: String? = null,
    var id: String? = null,
    var type: String? = null,
    var nickmane: String? = null
)
