package com.bkalysh.devicer2.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bkalysh.devicer2.R
import com.bkalysh.devicer2.database.models.Device
import com.bkalysh.devicer2.databinding.FragmentSmartPlugControlBinding
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

class SmartPlugControlFragment(private val device: Device?, private val viewModel: DeviceInfoViewModel) : Fragment() {
    private lateinit var binding: FragmentSmartPlugControlBinding
    private lateinit var scope: CoroutineScope

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        scope = CoroutineScope(Dispatchers.IO)
        binding = FragmentSmartPlugControlBinding.inflate(layoutInflater)
        setupWattageRequester()
        return binding.root
    }

    private fun setupWattageRequester(): Job {
        return scope.launch {
            updateWattage(requestWattage())
            while (isActive) {
                val wattage = requestWattage()
                updateWattage(wattage)
                delay(500L)
            }
        }
    }

    private suspend fun updateWattage(wattage: Int) {
        withContext(Dispatchers.Main) {
            binding.tvWattage.text =
                requireContext().getString(R.string.wattage, wattage)
        }
    }

    private suspend fun requestWattage(): Int {
        JWT.getJwtToken(requireContext())?.let { token ->
            device?.let {
                return viewModel.getSmartPlugWattage(token, device)
            }
        }
        return 0
    }

    override fun onDestroyView() {
        scope.cancel()
        super.onDestroyView()
    }
}