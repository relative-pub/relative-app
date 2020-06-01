package br.com.devteam.relative.interfaces

interface  DTO<T> {
     fun toDomain(): T
}