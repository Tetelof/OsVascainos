package com.example.osvascainos

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        val sharedPrefs = getSharedPreferences("LoggedIn", Context.MODE_PRIVATE)
        if (sharedPrefs.getBoolean("alreadyLogged", false)){
            val message_intent = Intent(this, Home::class.java)
            intent.putExtra("token", sharedPrefs.getString("token","defaultValue"))
            finish()
            startActivity(message_intent)
        }else {
            val intent = Intent(this, LoginPage::class.java)
            finish()
            startActivity(intent)
        }
    }
}