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
import com.omkar.reminder.databinding.FragmentGeneralInfoBinding

class GeneralInfoFragment : Fragment() {

    private lateinit var binding: FragmentGeneralInfoBinding
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
        binding = FragmentGeneralInfoBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        displayValue()
        binding.cardViewDev.setOnClickListener { showEditDialog(PREF_DEV_DAY) }
        binding.cardViewFastingDay.setOnClickListener { showEditDialog(PREF_FASTING_DAY) }
        binding.cardViewUpcomingBirthday.setOnClickListener { showEditDialog(PREF_UPCOMING_BIRTHDAY) }
    }

    private fun displayValue() {
        binding.textViewDevDayValue.text = preferences.getString(PREF_DEV_DAY, null)
        binding.textViewFastingDayValue.text = preferences.getString(PREF_FASTING_DAY, null)
        binding.textViewUpcomingBirthdayValue.text =
            preferences.getString(PREF_UPCOMING_BIRTHDAY, null)
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
        const val FILE_NAME = "general_info"
        const val PREF_DEV_DAY = "pref_dev_day"
        const val PREF_FASTING_DAY = "pref_fasting_day"
        const val PREF_UPCOMING_BIRTHDAY = "pref_upcoming_birthday"
    }
}