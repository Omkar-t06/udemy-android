package com.omkar.recordkeeper.running

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.omkar.recordkeeper.databinding.FragmentRunningBinding
import com.omkar.recordkeeper.editRecords.EditRecordActivity
import com.omkar.recordkeeper.editRecords.INTENT_EXTRA_SCREEN_DATA

class RunningFragment : Fragment() {

    private lateinit var binding: FragmentRunningBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRunningBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupClickListener()
    }

    override fun onResume() {
        super.onResume()
        displayRecord()
    }

    private fun setupClickListener() {
        binding.container5km.setOnClickListener { launchRunningRecordScreen(TITLE_5_KM) }
        binding.container10km.setOnClickListener { launchRunningRecordScreen(TITLE_10_KM) }
        binding.containerHalfMarathon.setOnClickListener {
            launchRunningRecordScreen(
                TITLE_HALF_MARATHON
            )
        }
        binding.containerMarathon.setOnClickListener { launchRunningRecordScreen(TITLE_MARATHON) }
    }

    private fun displayRecord() {
        val runningPreferences =
            requireContext().getSharedPreferences(FILENAME, Context.MODE_PRIVATE)

        binding.textView5kmValue.text = runningPreferences.getString(
            "$TITLE_5_KM ${EditRecordActivity.SHARED_PREFERENCE_RECORD_KEY}",
            null
        )
        binding.textView5kmDate.text = runningPreferences.getString(
            "$TITLE_5_KM ${EditRecordActivity.SHARED_PREFERENCE_DATE_KEY}",
            null
        )
        binding.textView10kmValue.text = runningPreferences.getString(
            "$TITLE_10_KM ${EditRecordActivity.SHARED_PREFERENCE_RECORD_KEY}",
            null
        )
        binding.textView10kmDate.text = runningPreferences.getString(
            "$TITLE_10_KM ${EditRecordActivity.SHARED_PREFERENCE_DATE_KEY}",
            null
        )
        binding.textViewHalfMarathonValue.text =
            runningPreferences.getString(
                "$TITLE_HALF_MARATHON ${EditRecordActivity.SHARED_PREFERENCE_RECORD_KEY}",
                null
            )
        binding.textViewHalfMarathonDate.text =
            runningPreferences.getString(
                "$TITLE_HALF_MARATHON ${EditRecordActivity.SHARED_PREFERENCE_DATE_KEY}",
                null
            )
        binding.textViewMarathonValue.text = runningPreferences.getString(
            "$TITLE_MARATHON ${EditRecordActivity.SHARED_PREFERENCE_RECORD_KEY}",
            null
        )
        binding.textViewMarathonDate.text = runningPreferences.getString(
            "$TITLE_MARATHON ${EditRecordActivity.SHARED_PREFERENCE_DATE_KEY}",
            null
        )
    }

    private fun launchRunningRecordScreen(distance: String) {
        val intent = Intent(context, EditRecordActivity::class.java)
        intent.putExtra(
            INTENT_EXTRA_SCREEN_DATA,
            EditRecordActivity.ScreenData(distance, FILENAME, "Time")
        )
        startActivity(intent)
    }

    companion object {
        const val FILENAME = "running"
        const val TITLE_5_KM = "5km"
        const val TITLE_10_KM = "10km"
        const val TITLE_HALF_MARATHON = "Half Marathon"
        const val TITLE_MARATHON = "Marathon"
    }
}