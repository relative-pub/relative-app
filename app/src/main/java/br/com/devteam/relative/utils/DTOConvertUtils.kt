package br.com.devteam.relative.utils

import br.com.devteam.relative.interfaces.DTO

fun <T> listDTOToDomainList(listDTO: List<DTO<T>>?): List<T> {
    val list = mutableListOf<T>()
    listDTO?.map { item ->
        list.add(item?.toDomain())
    }
    return list.toList()
}