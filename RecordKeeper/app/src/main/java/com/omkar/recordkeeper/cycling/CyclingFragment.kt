package com.omkar.recordkeeper.cycling

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.omkar.recordkeeper.CYCLING
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
                "Longest Ride",
                "Distance"
            )
        }
        binding.containerBiggestClimb.setOnClickListener {
            launchRunningRecordScreen(
                "Biggest Climb",
                "Height"
            )
        }
        binding.containerBestAverageSpeed.setOnClickListener {
            launchRunningRecordScreen(
                "Best Average Speed",
                "Average Speed"
            )
        }
    }

    private fun displayRecord() {
        val cyclingPreferences =
            requireContext().getSharedPreferences(
                CYCLING, Context.MODE_PRIVATE
            )

        binding.textViewLongestRideValue.text =
            cyclingPreferences.getString("Longest Ride record", null)
        binding.textViewLongestRideDate.text =
            cyclingPreferences.getString("Longest Ride date", null)
        binding.textViewBiggestClimbValue.text =
            cyclingPreferences.getString("Biggest Climb record", null)
        binding.textViewBiggestClimbDate.text =
            cyclingPreferences.getString("Biggest Climb date", null)
        binding.textViewBestAverageSpeedValue.text =
            cyclingPreferences.getString("Best Average Speed record", null)
        binding.textViewBestAverageSpeedDate.text =
            cyclingPreferences.getString("Best Average Speed date", null)
    }

    private fun launchRunningRecordScreen(record: String, recordFieldHint: String) {
        val intent = Intent(context, EditRecordActivity::class.java)
        intent.putExtra(
            INTENT_EXTRA_SCREEN_DATA,
            EditRecordActivity.ScreenData(record, CYCLING, recordFieldHint)
        )
        startActivity(intent)
    }
}