package com.example.osvascainos.retrofit

data class LoginResponse (
    val email: String,
    val erro: String,
    val id: Int,
    val nome: String,
    val sucesso: Boolean
)
