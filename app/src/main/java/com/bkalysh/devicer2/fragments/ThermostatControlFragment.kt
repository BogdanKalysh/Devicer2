package com.bkalysh.devicer2.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bkalysh.devicer2.database.models.Device
import com.bkalysh.devicer2.databinding.FragmentThermostatControlBinding
import com.bkalysh.devicer2.viewmodels.DeviceInfoViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.ext.android.viewModel

class ThermostatControlFragment(val device: Device?) : Fragment() {
    private lateinit var binding: FragmentThermostatControlBinding
    private val viewModel: DeviceInfoViewModel by viewModel()
    private val scope = CoroutineScope(Dispatchers.IO)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentThermostatControlBinding.inflate(layoutInflater)
        return binding.root
    }
}