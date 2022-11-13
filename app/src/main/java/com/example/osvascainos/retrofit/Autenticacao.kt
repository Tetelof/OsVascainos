package com.example.osvascainos.retrofit

data class Autenticacao (
    val message: String,
    val status: Boolean,
    val token: String,
    val user: UserX
)
