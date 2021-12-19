package com.dicoding.worldofgames.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.worldofgames.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
    }
}