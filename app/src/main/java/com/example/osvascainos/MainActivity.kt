package com.example.osvascainos

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.example.osvascainos.retrofit.RetrofitService


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        val sharedPrefs = getSharedPreferences("LoggedIn", Context.MODE_PRIVATE)
        if (sharedPrefs.getBoolean("alreadyLogged", false)){
            val home = Intent(this, Menu::class.java)
            intent.putExtra("token", sharedPrefs.getString("token","defaultValue"))
            finish()
            startActivity(home)
        }else {
            val intent = Intent(this, LoginPage::class.java)
            finish()
            startActivity(intent)
        }
    }
}