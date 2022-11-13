package com.example.osvascainos.retrofit

data class DHT11Response(
    var status: Boolean,
    var temperatura: Int,
    var umidade: Int
)
