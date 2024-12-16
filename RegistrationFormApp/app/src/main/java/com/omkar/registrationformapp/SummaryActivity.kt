package com.omkar.registrationformapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.omkar.registrationformapp.databinding.ActivitySummaryBinding

@Suppress("DEPRECATION")
class SummaryActivity : AppCompatActivity() {

    private lateinit var summaryViewBinding: ActivitySummaryBinding
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        summaryViewBinding = ActivitySummaryBinding.inflate(layoutInflater)
        setContentView(summaryViewBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.summary_layout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        retrieveUser()
        displayUser()
        setupClickListener()
    }

    private fun retrieveUser() {
        user = intent.getSerializableExtra("user") as User
    }

    private fun displayUser() {
        summaryViewBinding.textViewUserFullName.text = user.getFullName()
        summaryViewBinding.textViewUserEmail.text = user.email
        summaryViewBinding.textViewUserPhone.text = user.phone
    }

    private fun setupClickListener() {
        summaryViewBinding.textViewUserEmail.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:${user.email}")
            startActivity(intent)
        }
        summaryViewBinding.textViewUserPhone.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:${user.phone}")
            startActivity(intent)
        }
    }

}