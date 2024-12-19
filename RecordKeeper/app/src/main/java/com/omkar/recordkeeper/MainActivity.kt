package com.omkar.recordkeeper

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.commit
import com.google.android.material.navigation.NavigationBarView
import com.omkar.recordkeeper.cycling.CyclingFragment
import com.omkar.recordkeeper.databinding.ActivityMainBinding
import com.omkar.recordkeeper.running.RunningFragment

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.reset_running -> {
            Toast.makeText(this, "Clicked Running", Toast.LENGTH_SHORT).show()
            true
        }

        R.id.reset_cycling -> {
            Toast.makeText(this, "Clicked Cycling", Toast.LENGTH_SHORT).show()
            true
        }

        R.id.reset_all -> {
            Toast.makeText(this, "Clicked All", Toast.LENGTH_SHORT).show()
            true
        }

        else -> super.onOptionsItemSelected(item)
    }


    private fun onRunningClicked(): Boolean {
        supportFragmentManager.commit {
            replace(
                R.id.frame_content, RunningFragment()
            )
        }
        return true
    }

    private fun onCyclingClicked(): Boolean {
        supportFragmentManager.commit {
            replace(
                R.id.frame_content, CyclingFragment()
            )
        }
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.nav_cycling -> onCyclingClicked()
        R.id.nav_running -> onRunningClicked()
        else -> false
    }
}