package com.example.desafio4_firebase.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.desafio4_firebase.R
import com.example.desafio4_firebase.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = binding.includeToolbar.tbMain
        setSupportActionBar(toolbar)

        navController = findNavController(R.id.navHostMain)

        appBarConfiguration = AppBarConfiguration
            .Builder(R.id.loginFragment, R.id.homeFragment)
            .build()

        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    //Impedir de voltar em certos fragments
    override fun onBackPressed() {
        if (!podeVoltar()) {
            return
        }
        super.onBackPressed()
    }

    private fun podeVoltar(): Boolean {
        return when (navController.currentDestination?.id) {
            R.id.cadastroFragment, R.id.jogoFragment, R.id.editarJogoFragment -> {
                true
            }
            else -> false
        }
    }
}