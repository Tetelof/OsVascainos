package com.example.osvascainos.retrofit

import com.example.osvascainos.retrofit.*
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RetrofitService {
    @POST("/api/login")
    fun createPost(@Body login: Login) : Call<LoginResponse>

    @GET("api/temperatura-umidade")
    fun getTempUmidity() : Call<DHT11Response>

    @GET("api/leitor-rfid/{id}")
    fun getRfid(@Path("id") id: Int) : Call<RFIDResponse>


    @GET("api/sensor-infravermelho")
    fun getSensorInfravermelho() : Call<InfraResponse>

    @GET("api/sensor-gas")
    fun getSensorGas() : Call<GasResponse>

    @GET("api/sensor-voltagem")
    fun getVoltagem() : Call<VoltagemResponse>

    @GET("api/sensor-amperagem")
    fun getAmperagem() : Call<AmperagemResponse>

    @POST("api/cadastro/leitor-rfid")
    fun registerRfid(@Body user: CadastroRfid) : Call<RegisterRfidResponse>

    @POST("/api/criar-usuario")
    fun registerUser(@Body user: RegisterUser) : Call<RegisterUserResponse>


    companion object {
        private var base_url: String = "http://127.0.0.0:6969"
        private var retrofit: RetrofitService = Retrofit.Builder()
            .baseUrl(base_url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetrofitService::class.java)
        fun getRetrofitInstance() : RetrofitService {
            return retrofit
        }
        fun changeRetrofitBaseUrl(newUrl: String){
            retrofit = Retrofit.Builder()
                .baseUrl(newUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RetrofitService::class.java)
        }
    }
}
