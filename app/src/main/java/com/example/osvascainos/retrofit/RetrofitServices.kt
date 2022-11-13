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
    fun createPost(@Body login: Login) : Call<LoginResponse>

    @GET("api/temperatura-umidade")
    fun getTempUmidity() : Call<DHT11Response>

    @GET("api/leitor-rfid")
    fun getRfid() : Call<RFIDResponse>

    @GET("api/sensor-infravermelho")
    fun getSensorInfravermelho() : Call<InfraResponse>

    @GET("api/sensor-gas")
    fun getSensorGas() : Call<GasResponse>

    @GET("api/sensor-voltagem")
    fun getVoltagem() : Call<VoltagemResponse>

    @GET("api/sensor-amperagem")
    fun getAmperagem() : Call<AmperagemResponse>

    @POST("api/cadastro/leitor-rfid")
    fun registerRfid(@Body id: Int) : Call<RegisterRfidResponse>


    companion object{
        private val retrofitService : RetrofitService by lazy {
            val retrofit = Retrofit.Builder()
                .baseUrl("http://192.168.15.157:6969/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            retrofit.create(RetrofitService::class.java)
        }
        fun getRetrofitInstance() : RetrofitService {
            return retrofitService
        }
    }
}
