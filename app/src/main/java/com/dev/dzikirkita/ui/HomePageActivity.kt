package com.dev.dzikirkita.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.dev.dzikirkita.R
import com.dev.dzikirkita.ui.beranda.BerandaFragment
import com.dev.dzikirkita.ui.catatan.CatatanFragment
import com.dev.dzikirkita.ui.todo_list.TodoListFragment
import nl.joery.animatedbottombar.AnimatedBottomBar

class HomePageActivity : AppCompatActivity() {
    private lateinit var animatedBottomBar: AnimatedBottomBar
    var gender: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_page)

        animatedBottomBar = findViewById(R.id.animatedBottomBar)
        gender = savedInstanceState?.getString(EXTRA_GENDER)
            ?: intent.getStringExtra(EXTRA_GENDER)
        if (savedInstanceState == null) {
            switchToFragment(BerandaFragment.newInstance(gender))
        }

        animatedBottomBar.setOnTabSelectListener(object : AnimatedBottomBar.OnTabSelectListener {
            override fun onTabSelected(
                lastIndex: Int,
                lastTab: AnimatedBottomBar.Tab?,
                newIndex: Int,
                newTab: AnimatedBottomBar.Tab,
            ) {
                when (newTab.id) {
                    R.id.iBeranda -> {
                        switchToFragment(BerandaFragment.newInstance(gender))
                    }
                    R.id.iCatatan -> switchToFragment(CatatanFragment())
                    R.id.iAktivitas -> switchToFragment(TodoListFragment())
                }
            }
        })
    }

    private fun switchToFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(EXTRA_GENDER, gender)
    }

    companion object {
        private val TAG = HomePageActivity::class.java.simpleName
        const val EXTRA_GENDER = "extra_gender"
    }
}