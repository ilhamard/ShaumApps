package com.example.shaumapps.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.shaumapps.R
import nl.joery.animatedbottombar.AnimatedBottomBar

class HomePageActivity : AppCompatActivity() {
    private lateinit var animatedBottomBar: AnimatedBottomBar
    var fragmentManager: FragmentManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_page)
        animatedBottomBar = findViewById(R.id.animatedBottomBar)
        if (savedInstanceState == null) {
            animatedBottomBar.selectTabById(R.id.iBeranda, true)
            fragmentManager = getSupportFragmentManager()
            val homeFragment = HomeFragment()
            fragmentManager!!.beginTransaction().replace(R.id.fragment_container, homeFragment)
                .commit()
        }
        animatedBottomBar.setOnTabSelectListener(object : AnimatedBottomBar.OnTabSelectListener {
            override fun onTabSelected(
                lastIndex: Int,
                lastTab: AnimatedBottomBar.Tab?,
                newIndex: Int,
                newTab: AnimatedBottomBar.Tab
            ) {
                var fragment: Fragment? = null
                when (newTab.id) {
                    R.id.iBeranda -> fragment = HomeFragment()
                    R.id.iCatatan -> fragment = CatatanFragment()
                    R.id.iKalender -> fragment = KalenderFragment()
                    R.id.iPengaturan -> fragment = SettingFragment()
                }
                if (fragment != null) {
                    fragmentManager = supportFragmentManager
                    fragmentManager!!.beginTransaction().replace(R.id.fragment_container, fragment)
                        .commit()
                } else {
                    Log.e(TAG, "Error in creating Fragment")
                }
            }
        })
    }

    companion object {
        private val TAG = HomePageActivity::class.java.simpleName
    }
}