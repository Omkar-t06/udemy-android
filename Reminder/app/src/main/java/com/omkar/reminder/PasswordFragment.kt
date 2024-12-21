package com.omkar.reminder

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.omkar.reminder.databinding.DialogEditReminderBinding
import com.omkar.reminder.databinding.FragmentPasswordBinding

class PasswordFragment : Fragment() {

    private lateinit var binding: FragmentPasswordBinding
    private val preferences by lazy {
        requireContext().getSharedPreferences(
            FILE_NAME,
            Context.MODE_PRIVATE
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPasswordBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        displayValue()
        binding.cardViewWifi.setOnClickListener { showEditDialog(PREF_WIFI) }
        binding.cardViewLaptopPin.setOnClickListener { showEditDialog(PREF_PIN) }
        binding.cardViewBiometric.setOnClickListener { showEditDialog(PREF_BIO_ID) }
    }

    private fun displayValue() {
        binding.textViewWifiValue.text = preferences.getString(PREF_WIFI, null)
        binding.textViewLaptopPinValue.text = preferences.getString(PREF_PIN, null)
        binding.textViewBiometricNoValue.text = preferences.getString(PREF_BIO_ID, null)
    }

    private fun showEditDialog(preferenceKey: String) {
        val dialogBinding = DialogEditReminderBinding.inflate(requireActivity().layoutInflater)
        dialogBinding.editTextValue.setText(preferences.getString(preferenceKey, null))

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Update Value")
            .setView(dialogBinding.root)
            .setPositiveButton("Save") { _, _ ->
                preferences.edit {
                    putString(preferenceKey, dialogBinding.editTextValue.text?.toString())
                }
                displayValue()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    companion object {
        const val FILE_NAME = "password"
        const val PREF_WIFI = "pref_wifi"
        const val PREF_PIN = "pref_pin"
        const val PREF_BIO_ID = "pref_bio_id"
    }
}