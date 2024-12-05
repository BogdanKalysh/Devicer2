package com.bkalysh.devicer2.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import com.bkalysh.devicer2.R
import com.bkalysh.devicer2.database.models.Device
import com.bkalysh.devicer2.databinding.FragmentThermostatControlBinding
import com.bkalysh.devicer2.utils.JWT
import com.bkalysh.devicer2.viewmodels.DeviceInfoViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ThermostatControlFragment(val device: Device?, private val viewModel: DeviceInfoViewModel) : Fragment() {
    private lateinit var binding: FragmentThermostatControlBinding
    private lateinit var scope: CoroutineScope
    private var updaterJob: Job = Job()
    private var isPowered = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        scope = CoroutineScope(Dispatchers.IO)
        binding = FragmentThermostatControlBinding.inflate(layoutInflater)
        updaterJob = setupTempsRequester()
        setupSliderObserver()
        setupPowerStateObserver()
        return binding.root
    }

    private fun setupTempsRequester(): Job {
        scope.launch {
            val goalTemp = async { requestGoalTemperature() }
            val currentTemp = async { requestCurrentTemperature() }
            updateSliderPercentage(goalTemp.await())
            updateCurrentTemperature(currentTemp.await())
        }

        return scope.launch {
            while (isActive) {
                val goalTemp = async { requestGoalTemperature() }
                val currentTemp = async { requestCurrentTemperature() }

                if (binding.sbTemperatureSlider.progress != goalTemp.await()) {
                    updateSliderPercentage(goalTemp.await())
                }
                if (binding.tvGoalTemperature.text != currentTemp.await().toString()) {
                    updateCurrentTemperature(currentTemp.await())
                }
                updateHeatingState(currentTemp.await(), goalTemp.await())
                delay(1000L)
            }
        }
    }

    private suspend fun updateHeatingState(currentTemp: Int, goalTemp: Int) {
        withContext(Dispatchers.Main) {
            if (!isPowered) {
                binding.tvHeatingState.text = "Idle"
            } else {
                if (currentTemp < goalTemp) {
                    binding.tvHeatingState.text = "Heating"
                } else if (currentTemp > goalTemp) {
                    binding.tvHeatingState.text = "Cooling"
                } else {
                    binding.tvHeatingState.text = "Idle"
                }
            }
        }
    }

    private suspend fun updateSliderPercentage(percentage: Int) {
        withContext(Dispatchers.Main) {
            binding.sbTemperatureSlider.progress = percentage
            binding.tvGoalTemperature.text = percentage.toString()
        }
    }

    private suspend fun updateCurrentTemperature(percentage: Int) {
        withContext(Dispatchers.Main) {
            binding.tvCurrentTemperature.text =
                requireContext().getString(R.string.temperature_celsius, percentage)
        }
    }

    private suspend fun requestGoalTemperature(): Int {
        JWT.getJwtToken(requireContext())?.let { token ->
            device?.let {
                return viewModel.getThermostatGoalTemperature(token, device)
            }
        }
        return 0
    }

    private suspend fun requestCurrentTemperature(): Int {
        JWT.getJwtToken(requireContext())?.let { token ->
            device?.let {
                return viewModel.getThermostatCurrentTemperature(token, device)
            }
        }
        return 0
    }

    private fun setupSliderObserver() {
        binding.sbTemperatureSlider.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {

                    binding.tvGoalTemperature.text = progress.toString()
                    JWT.getJwtToken(requireContext())?.let { token ->
                        device?.let {
                            viewModel.setThermostatGoalTemperature(token, device, progress)
                        }
                    }
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                updaterJob.cancel()
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                updaterJob = setupTempsRequester()
            }
        })
    }

    private fun setupPowerStateObserver() {
        viewModel.currentDevice.observe(viewLifecycleOwner) { device ->
            device?.let {
                isPowered = device.isPowered
                if (device.isPowered != binding.sbTemperatureSlider.isEnabled) {
                    binding.sbTemperatureSlider.isEnabled = device.isPowered
                }
            }
        }
    }

    override fun onDestroyView() {
        scope.cancel()
        super.onDestroyView()
    }
}