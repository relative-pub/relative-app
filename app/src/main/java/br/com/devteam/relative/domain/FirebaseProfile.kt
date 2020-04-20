package br.com.devteam.relative.domain

import java.io.Serializable

data class FirebaseProfile(
    var email: String? = null,
    var name: String? = null,
    var phone: String? = null
): Serializable