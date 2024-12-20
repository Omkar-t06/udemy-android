package com.omkar.recordkeeper.editRecords

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.omkar.recordkeeper.R
import com.omkar.recordkeeper.databinding.ActivityEditRecordBinding
import java.io.Serializable

const val INTENT_EXTRA_SCREEN_DATA = "screen_data"


class EditRecordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditRecordBinding

    private val screenData by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra(INTENT_EXTRA_SCREEN_DATA, ScreenData::class.java) as ScreenData
        } else {
            @Suppress("DEPRECATION")
            intent.getSerializableExtra(INTENT_EXTRA_SCREEN_DATA) as ScreenData
        }
    }

    private val runningPreferences by lazy {
        getSharedPreferences(
            screenData.sharedPreferencesName,
            Context.MODE_PRIVATE
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEditRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupUI()
        displayRecords()
    }

    private fun setupUI() {
        title = "${screenData.record} Record"
        binding.editTextRecordLayout.hint = screenData.recordFieldHint
        binding.buttonSaveRecord.setOnClickListener {
            saveRecords()
            finish()
        }
        binding.buttonClearRecord.setOnClickListener {
            clearRecord()
            finish()
        }
    }

    private fun displayRecords() {
        binding.editTextRecord.setText(
            runningPreferences.getString(
                "${screenData.record} record",
                null
            )
        )
        binding.editTextDate.setText(
            runningPreferences.getString(
                "${screenData.record} date",
                null
            )
        )
    }

    private fun saveRecords() {
        val record = binding.editTextRecord.text.toString()
        val date = binding.editTextDate.text.toString()

        runningPreferences.edit {
            putString("${screenData.record} record", record)
            putString("${screenData.record} date", date)
        }
    }

    private fun clearRecord() {
        runningPreferences.edit {
            remove("${screenData.record} record")
            remove("${screenData.record} date")
        }
    }

    data class ScreenData(
        val record: String,
        val sharedPreferencesName: String,
        val recordFieldHint: String
    ) : Serializable
}