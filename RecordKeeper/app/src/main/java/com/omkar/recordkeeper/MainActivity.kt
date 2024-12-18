package com.omkar.recordkeeper

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.commit
import com.google.android.material.navigation.NavigationBarView
import com.omkar.recordkeeper.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {

    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        activityMainBinding.bottomNav.setOnItemSelectedListener(this)
    }

    private fun onRunningClicked() {
        supportFragmentManager.commit {
            replace(
                R.id.frame_content, RunningFragment()
            )
        }
    }

    private fun onCyclingClicked() {
        supportFragmentManager.commit {
            replace(
                R.id.frame_content, CyclingFragment()
            )
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.nav_cycling) {
            onCyclingClicked()
            return true
        } else if (item.itemId == R.id.nav_running) {
            onRunningClicked()
            return true
        } else {
            return false
        }
    }
}