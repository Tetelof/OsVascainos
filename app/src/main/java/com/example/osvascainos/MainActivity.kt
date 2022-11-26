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
        val sp = PreferenceManager.getDefaultSharedPreferences(this)
        RetrofitService.changeRetrofitBaseUrl(sp.getString("ip_rasp","http://127.0.0.1:6969")!!)
        val intent = Intent(this, LoginPage::class.java)
        finish()
        startActivity(intent)
    }
}