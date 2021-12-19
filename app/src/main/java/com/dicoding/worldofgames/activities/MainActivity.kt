package com.dicoding.worldofgames.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.dicoding.worldofgames.R
import com.dicoding.worldofgames.fragments.FavoriteFragment
import com.dicoding.worldofgames.fragments.HomeFragment
import com.dicoding.worldofgames.fragments.ProfileFragment
import me.ibrahimsn.lib.SmoothBottomBar

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        val bottomBar = findViewById<SmoothBottomBar>(R.id.bottom_menu)

        val homeFragment = HomeFragment()
        val favoriteFragment = FavoriteFragment()
        val profileFragment = ProfileFragment()
        showFragment(homeFragment)

        bottomBar.setOnItemSelectedListener {
            Log.d("item", it.toString())
            when (it) {
                0 -> {
                    showFragment(homeFragment)
                    hideFragment(favoriteFragment)
                    hideFragment(profileFragment)
                }
                1 -> {
                    showFragment(favoriteFragment)
                    hideFragment(homeFragment)
                    hideFragment(profileFragment)
                }
                2 -> {
                    showFragment(profileFragment)
                    hideFragment(homeFragment)
                    hideFragment(favoriteFragment)
                }
            }
        }
    }

    private fun checkIfFragmentExist(fragment: Fragment): Boolean {
        val actualFragment = supportFragmentManager.findFragmentByTag(fragment.javaClass.simpleName)
        return actualFragment != null
    }

    private fun showFragment(fragment: Fragment) {
        if (checkIfFragmentExist(fragment)) {
            supportFragmentManager.beginTransaction()
                .show(fragment).commit()
        } else {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, fragment, fragment.javaClass.simpleName)
                .show(fragment).commit()
        }
    }

    private fun hideFragment(fragment: Fragment) {
        if (checkIfFragmentExist(fragment)) {
            supportFragmentManager.beginTransaction()
                .hide(fragment).commit()
        }
    }
}