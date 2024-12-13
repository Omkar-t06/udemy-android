package com.omkar.selfpromotionapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.omkar.selfpromotionapp.databinding.ActivityPreviewBinding

@Suppress("DEPRECATION")
class PreviewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPreviewBinding
    private lateinit var message: Message
    private lateinit var messagePreviewText: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPreviewBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.preview_message_activity)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        displayMessage()
        setupButton()
    }

    private fun displayMessage() {
        message = intent.getSerializableExtra("Message") as Message
        messagePreviewText = """
                Hi ${message.contactName},
                
                My name is ${message.myDisplayName} and I am ${message.getFullJobDescription()}.
                
                I have a portfolio of apps to demonstrate my technical skills that I can on request.
                
                I am able to start a new position ${message.getAvailability()}.
                
                Please get in touch if you have any suitable roles for me.
                
                Thanks and best regards.
            """.trimIndent()

        binding.textViewMessage.text = messagePreviewText
    }

    private fun setupButton() {
        binding.buttonSendMessage.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("smsto: ${message.contactNumber}")
                putExtra("sms_body", messagePreviewText)
            }
            startActivity(intent)
        }
    }
}