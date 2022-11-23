package com.example.osvascainos.retrofit

import android.util.Log
import androidx.preference.PreferenceManager
import com.example.osvascainos.retrofit.*
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url

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

    @POST("/api/criar-usuario")
    fun registerUser(@Body user: RegisterUser) : Call<RegisterUserResponse>


    companion object {
        private var base_url: String = "http://192.168.3.157:6969"
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
