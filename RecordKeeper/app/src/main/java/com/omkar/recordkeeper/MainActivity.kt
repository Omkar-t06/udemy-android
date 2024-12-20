package com.omkar.recordkeeper

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.commit
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.snackbar.Snackbar
import com.omkar.recordkeeper.cycling.CyclingFragment
import com.omkar.recordkeeper.databinding.ActivityMainBinding
import com.omkar.recordkeeper.running.RunningFragment

const val RUNNING = "running"
const val CYCLING = "cycling"
const val ALL = "all"

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val menuClickedHandled = when (item.itemId) {
            R.id.reset_running -> {
                showConfirmationDialog(RUNNING)
                true
            }

            R.id.reset_cycling -> {
                showConfirmationDialog(CYCLING)
                true
            }

            R.id.reset_all -> {
                showConfirmationDialog(ALL)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }

        return menuClickedHandled
    }

    private fun refreshCurrentFragment() {
        when (activityMainBinding.bottomNav.selectedItemId) {
            R.id.nav_running -> onRunningClicked()
            R.id.nav_cycling -> onCyclingClicked()
            else -> {}
        }
    }

    private fun showConfirmationDialog(selection: String) {
        AlertDialog.Builder(this)
            .setTitle("Reset $selection records")
            .setPositiveButton("Yes") { _, _ ->
                when (selection) {
                    ALL -> {
                        getSharedPreferences(CYCLING, Context.MODE_PRIVATE).edit { clear() }
                        getSharedPreferences(RUNNING, Context.MODE_PRIVATE).edit { clear() }
                    }

                    else -> getSharedPreferences(selection, Context.MODE_PRIVATE).edit { clear() }
                }
                refreshCurrentFragment()
                shoeConfirmation()
            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun shoeConfirmation() {
        val snackBar = Snackbar.make(
            activityMainBinding.frameContent,
            "Records cleared successfully",
            Snackbar.LENGTH_LONG
        )
        snackBar.anchorView = activityMainBinding.bottomNav
        snackBar.show()
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