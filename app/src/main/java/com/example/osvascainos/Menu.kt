package com.example.osvascainos

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.example.osvascainos.databinding.ActivityMenuBinding
import com.example.osvascainos.retrofit.RetrofitService

class Menu : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMenu.toolbar)


        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_menu)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_temp,
                R.id.nav_gas,
                R.id.nav_potencia,
                R.id.nav_amperagem,
                R.id.nav_tensao,
                R.id.nav_infrared
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val sp = PreferenceManager.getDefaultSharedPreferences(this)
        RetrofitService.changeRetrofitBaseUrl(sp.getString("ip_rasp","http://127.0.0.1:6969")!!)
        val prefListener =
            OnSharedPreferenceChangeListener { prefs, key ->
                if (key == "ip_rasp") {
                    RetrofitService.changeRetrofitBaseUrl(
                        prefs.getString("ip_rasp","http://127.0.0.1:6969")!!
                    )
                }
            }
        sp.registerOnSharedPreferenceChangeListener(prefListener)

//        val userName: TextView = findViewById(R.id.user_name)
//        userName.text = Data.user.nome
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_logout -> {
                val intent = Intent(this, MainActivity::class.java)
                val sharedPrefs = getSharedPreferences("LoggedIn", Context.MODE_PRIVATE)
                val edit = sharedPrefs.edit()
                edit.remove("login")
                edit.remove("senha")
                edit.remove("alreadyLogged")
                edit.remove("token")
                edit.commit()
                finish()
                startActivity(intent)
            }
            R.id.action_settings ->{
                findNavController(R.id.nav_host_fragment_content_menu).navigate(R.id.settingsFragment)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_menu)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}