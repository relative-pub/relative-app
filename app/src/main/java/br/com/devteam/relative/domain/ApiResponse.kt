package br.com.devteam.relative.domain

data class ApiResponse<T>(
    val success: Boolean = true,
    val userMessage: String? = null,
    val techMessage: String? = null,
    val data: T? = null
)
