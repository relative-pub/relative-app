package br.com.devteam.relative.domain

data class DomainType(
    var id: Int? = null,
    var name: String? = null,
    var description: String? = null,
    var parentId: String? = null
)