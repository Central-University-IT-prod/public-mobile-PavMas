package com.trifcdr.lifestylehub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.trifcdr.lifestylehub.databinding.ActivityMainBinding
import com.trifcdr.lifestylehub.presentation.fragments.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var bottomNavigation: BottomNavigationView

    private val vm: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bottomNavigation = binding.bottomNavigationView
        val navController = this.findNavController(R.id.nav_host_fragment)
        val navView: BottomNavigationView = binding.bottomNavigationView
        navView.setupWithNavController(navController)



    }
}