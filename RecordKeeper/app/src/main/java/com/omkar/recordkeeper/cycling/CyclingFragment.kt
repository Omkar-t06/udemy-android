package com.omkar.recordkeeper.cycling

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.omkar.recordkeeper.databinding.FragmentCyclingBinding
import com.omkar.recordkeeper.editRecords.EditRecordActivity
import com.omkar.recordkeeper.editRecords.INTENT_EXTRA_SCREEN_DATA

class CyclingFragment : Fragment() {

    private lateinit var binding: FragmentCyclingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCyclingBinding.inflate(inflater, container, false)
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
        binding.containerLongestRide.setOnClickListener {
            launchRunningRecordScreen(
                TITLE_LONGEST_RIDE,
                "Distance"
            )
        }
        binding.containerBiggestClimb.setOnClickListener {
            launchRunningRecordScreen(
                TITLE_BIGGEST_CLIMB,
                "Height"
            )
        }
        binding.containerBestAverageSpeed.setOnClickListener {
            launchRunningRecordScreen(
                TITLE_BEST_AVERAGE_SPEED,
                "Average Speed"
            )
        }
    }

    private fun displayRecord() {
        val cyclingPreferences =
            requireContext().getSharedPreferences(
                FILENAME, Context.MODE_PRIVATE
            )

        binding.textViewLongestRideValue.text =
            cyclingPreferences.getString(
                "$TITLE_LONGEST_RIDE ${EditRecordActivity.SHARED_PREFERENCE_RECORD_KEY}",
                null
            )
        binding.textViewLongestRideDate.text =
            cyclingPreferences.getString(
                "$TITLE_LONGEST_RIDE ${EditRecordActivity.SHARED_PREFERENCE_DATE_KEY}",
                null
            )
        binding.textViewBiggestClimbValue.text =
            cyclingPreferences.getString(
                "$TITLE_BIGGEST_CLIMB ${EditRecordActivity.SHARED_PREFERENCE_RECORD_KEY}",
                null
            )
        binding.textViewBiggestClimbDate.text =
            cyclingPreferences.getString(
                "$TITLE_BIGGEST_CLIMB ${EditRecordActivity.SHARED_PREFERENCE_DATE_KEY}",
                null
            )
        binding.textViewBestAverageSpeedValue.text =
            cyclingPreferences.getString(
                "$TITLE_BEST_AVERAGE_SPEED ${EditRecordActivity.SHARED_PREFERENCE_RECORD_KEY}",
                null
            )
        binding.textViewBestAverageSpeedDate.text =
            cyclingPreferences.getString(
                "$TITLE_BEST_AVERAGE_SPEED ${EditRecordActivity.SHARED_PREFERENCE_DATE_KEY}",
                null
            )
    }

    private fun launchRunningRecordScreen(record: String, recordFieldHint: String) {
        val intent = Intent(context, EditRecordActivity::class.java)
        intent.putExtra(
            INTENT_EXTRA_SCREEN_DATA,
            EditRecordActivity.ScreenData(record, FILENAME, recordFieldHint)
        )
        startActivity(intent)
    }

    companion object {
        const val FILENAME = "cycling"
        const val TITLE_LONGEST_RIDE = "Longest Ride"
        const val TITLE_BIGGEST_CLIMB = "Biggest Climb"
        const val TITLE_BEST_AVERAGE_SPEED = "Best Average Speed"
    }
}