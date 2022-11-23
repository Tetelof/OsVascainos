package com.example.osvascainos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.osvascainos.retrofit.Login
import com.example.osvascainos.retrofit.LoginResponse
import com.example.osvascainos.retrofit.RetrofitService
import com.example.osvascainos.retrofit.UserX
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginPage : AppCompatActivity() {

    private lateinit var txtLogin : EditText
    private lateinit var txtSenha : EditText
    private lateinit var buttonLogin : Button
    private lateinit var buttonCreateUser : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        supportActionBar?.hide()

        txtLogin = findViewById(R.id.txt_login)
        txtSenha = findViewById(R.id.txt_senha)
        buttonLogin = findViewById(R.id.button_login)

        buttonLogin.setOnClickListener{
            val login = txtLogin.text.toString()
            val senha = txtSenha.text.toString()
            fazerLogin(login, senha, "dev")
        }

        buttonCreateUser = findViewById(R.id.button_create_user)
        buttonCreateUser.setOnClickListener{
            val intent = Intent(this@LoginPage, CreateUser::class.java)
            startActivity(intent)
        }
    }
    fun fazerLogin(login : String, senha : String) {
        val retrofit = RetrofitService.getRetrofitInstance()

        val login = Login(login = login, senha = senha)

        val post = retrofit.createPost(login)

        post.enqueue(object: Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if(response.isSuccessful){
                    Data.user = UserX(
                        email = response.body()!!.email,
                        id = response.body()!!.id,
                        nome = response.body()!!.nome,
                    )
                    val intent = Intent(this@LoginPage, Menu::class.java)
                    startActivity(intent)
                }else {
                    Log.d("TAG", "onResponse: " + response.message())
                    Toast.makeText(baseContext, "Não foi possivel fazer login. Verifique sua conexão!", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(baseContext, "Não foi possivel fazer login. Verifique sua conexão!", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun fazerLogin(login : String, senha : String, dev: String) {
        val intent = Intent(this@LoginPage, Menu::class.java)
        startActivity(intent)
    }
}