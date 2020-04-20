package br.com.devteam.relative.domain

data class ApiResponse(
    val success: Boolean = true,
    val userMessage: String? = null,
    val techMessage: String? = null
)
