package com.example.osvascainos

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.osvascainos.retrofit.Chat
import com.example.osvascainos.retrofit.ChatResposta
import com.example.osvascainos.retrofit.Contato
import com.example.osvascainos.retrofit.Token
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Home : AppCompatActivity() {

//    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userList: ArrayList<Chat>
//    private lateinit var adapter: UserAdapter
    private lateinit var logout: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        supportActionBar?.hide()
        val token = Token(token=intent.getStringExtra("token").toString())

        userList = ArrayList()
//        adapter = UserAdapter(this, userList)

//        userRecyclerView = findViewById(R.id.userRecyclerView)
//        userRecyclerView.layoutManager = LinearLayoutManager(this)
//        userRecyclerView.adapter = adapter

        for (chat in getChats()){
            userList.add(chat)
        }

        logout = findViewById<ImageView>(R.id.logout_button)
        logout.setOnClickListener{
            val intent = Intent(this@Home, MainActivity::class.java)

            val sharedPrefs = getSharedPreferences("LoggedIn", Context.MODE_PRIVATE)
            var edit = sharedPrefs.edit()

            edit.remove("login")
            edit.remove("senha")
            edit.remove("alreadyLogged")
            edit.remove("token")
            edit.commit()

            finish()
            startActivity(intent)
        }
    }

    fun getChats(token: Token) : String{
        val retrofit = RetrofitService.getRetrofitInstance()
        val post = retrofit.createPost(token)
        var listaChats = emptyList<Chat>()

        post.enqueue(object: Callback<ChatResposta> {
            override fun onResponse(call: Call<ChatResposta>, response: Response<ChatResposta>) {
                if(response.isSuccessful){
                    listaChats = response.body()!!.chat
                }else {
                    Log.d("TAG", "onResponse: " + response.message())
                }
            }
            override fun onFailure(call: Call<ChatResposta>, t: Throwable) {
                Toast.makeText(baseContext, t.message, Toast.LENGTH_SHORT).show()
            }
        })
        return "nani"
    }

    // Hardcode da response do API que não esta funcionando
    fun getChats() : ArrayList<Chat>{
        var listaChats = ArrayList<Chat>()

        for (i in 0..10){
            listaChats.add(Chat(
                "mensagem $i",
                Contato(
                    i,
                    "Contato $i",
                    "http://www.alertrack.com.br/api/teste_mobile/img/perfil1_.png"
                )
            ))
        }
        return listaChats
    }
}