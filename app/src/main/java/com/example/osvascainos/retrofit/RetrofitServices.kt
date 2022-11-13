package com.example.osvascainos

import com.example.osvascainos.retrofit.*
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RetrofitService {
    @POST("/api/login")
    fun createPost(@Body login: Login) : Call<Autenticacao>

    @POST("api/teste_mobile/chats.php")
    fun createPost(@Body token: Token) : Call<ChatResposta>

    @GET("api/teste_mobile/msgs.php?contact_id=1")
    fun getMensagens() : Call<MsgResposta>

    companion object{
        private val retrofitService : RetrofitService by lazy {
            val retrofit = Retrofit.Builder()
                .baseUrl("http://alertrack.com.br/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            retrofit.create(RetrofitService::class.java)
        }
        fun getRetrofitInstance() : RetrofitService {
            return retrofitService
        }
    }
}
