package br.com.devteam.relative.repository.dto

interface  DTO<T> {
     fun toDomain(): T
}