package com.bkalysh.devicer2.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import com.bkalysh.devicer2.R
import com.bkalysh.devicer2.database.models.Device
import com.bkalysh.devicer2.databinding.FragmentSmartLampControlBinding
import com.bkalysh.devicer2.utils.JWT
import com.bkalysh.devicer2.viewmodels.DeviceInfoViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SmartLampControlFragment(private val device: Device?, private val viewModel: DeviceInfoViewModel) : Fragment() {
    private lateinit var binding: FragmentSmartLampControlBinding
    private val scope = CoroutineScope(Dispatchers.IO)
    private var updaterJob: Job = Job()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSmartLampControlBinding.inflate(inflater, container, false)
        updaterJob = setupBrightnessRequester()
        setupSliderObserver()
        setupPowerStateObserver()
        return binding.root
    }

    private fun setupBrightnessRequester(): Job {
        return scope.launch {
            updateSliderPercentage(requestBrightness())
            while (isActive) {
                val brightness = requestBrightness()
                if (binding.sbBrightnessSlider.progress != brightness) {
                    updateSliderPercentage(brightness)
                }
                delay(1000L)
            }
        }
    }

    private suspend fun requestBrightness(): Int {
        JWT.getJwtToken(requireContext())?.let { token ->
            device?.let {
                return viewModel.getSmartLampBrightness(token, device)
            }
        }
        return 0
    }

    private suspend fun updateSliderPercentage(percentage: Int) {
        withContext(Dispatchers.Main) {
            binding.sbBrightnessSlider.progress = percentage
            binding.tvBrightnessPercentage.text =
                requireContext().getString(R.string.percentage, percentage)
        }
    }

    private fun setupSliderObserver() {
        binding.sbBrightnessSlider.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    updaterJob.cancel()
                    binding.tvBrightnessPercentage.text =
                        requireContext().getString(R.string.percentage, progress)
                     JWT.getJwtToken(requireContext())?.let { token ->
                        device?.let {
                            viewModel.setSmartLampBrightness(token, device, progress)
                        }
                    }
                    updaterJob = setupBrightnessRequester()
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun setupPowerStateObserver() {
        viewModel.currentDevice.observe(viewLifecycleOwner) { device ->
            device?.let {
                if (device.isPowered != binding.sbBrightnessSlider.isEnabled) {
                    binding.sbBrightnessSlider.isEnabled = device.isPowered
                }
            }
        }
    }

    override fun onDestroyView() {
        scope.cancel()
        super.onDestroyView()
    }
}