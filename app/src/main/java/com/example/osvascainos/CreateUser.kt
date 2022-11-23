package com.example.osvascainos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.osvascainos.retrofit.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateUser : AppCompatActivity() {
    private lateinit var login: EditText
    private lateinit var email: EditText
    private lateinit var nome: EditText
    private lateinit var senha: EditText
    private lateinit var buttonRegister : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)

        login = findViewById(R.id.register_login)
        email = findViewById(R.id.register_email)
        nome = findViewById(R.id.register_nome)
        senha = findViewById(R.id.register_senha)
        buttonRegister = findViewById(R.id.button_register)

        buttonRegister.setOnClickListener{
            val user = RegisterUser(
                login = login.text.toString(),
                email = email.text.toString(),
                nome = nome.text.toString(),
                senha = senha.text.toString()
            )
            register(user)
        }
    }

    fun register(user: RegisterUser) {
        val retrofit = RetrofitService.getRetrofitInstance()
        val post = retrofit.registerUser(user)
        post.enqueue(object: Callback<RegisterUserResponse> {
            override fun onResponse(call: Call<RegisterUserResponse>, response: Response<RegisterUserResponse>) {
                if(response.isSuccessful){
                    Toast.makeText(baseContext, "Usuario criado com sucesso.", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@CreateUser, LoginPage::class.java)
                    startActivity(intent)
                }else {
                    Log.d("TAG", "onResponse: " + response.message())
                    Toast.makeText(baseContext, "Não foi possivel criar usuario. Verifique sua conexão!", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<RegisterUserResponse>, t: Throwable) {
                Toast.makeText(baseContext, "Não foi possivel criar usuario. Verifique sua conexão!", Toast.LENGTH_SHORT).show()
            }
        })
    }
}