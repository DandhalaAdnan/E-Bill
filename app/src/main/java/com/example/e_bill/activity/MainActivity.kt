package com.example.e_bill.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.e_bill.R
import com.example.e_bill.databinding.ActivityMainBinding
import com.example.e_bill.viewModel.LoginDataSource
import com.example.e_bill.viewModel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = MainViewModel(LoginDataSource(this))
        window.statusBarColor = ContextCompat.getColor(this, R.color.status_bar_color)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.isLoading.value
            }
        }
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}