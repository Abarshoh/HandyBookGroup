package com.akbar.handybook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.akbar.handybook.databinding.ActivityMainBinding
import com.akbar.handybook.ui.SplashFirstFragment

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .add(R.id.main, SplashFirstFragment())
            .commit()
    }
}