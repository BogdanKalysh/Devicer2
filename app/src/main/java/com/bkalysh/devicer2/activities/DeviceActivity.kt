package com.bkalysh.devicer2.activities

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.bkalysh.devicer2.R
import com.bkalysh.devicer2.database.models.Device
import com.bkalysh.devicer2.databinding.ActivityDeviceBinding
import com.bkalysh.devicer2.fragments.SmartLampControlFragment
import com.bkalysh.devicer2.fragments.SmartPlugControlFragment
import com.bkalysh.devicer2.fragments.ThermostatControlFragment
import com.bkalysh.devicer2.mocked.api.db.models.unique.fields.SmartLampData
import com.bkalysh.devicer2.mocked.api.db.models.unique.fields.SmartPlugData
import com.bkalysh.devicer2.mocked.api.db.models.unique.fields.ThermostatData
import com.bkalysh.devicer2.utils.Constants.DEVICE_KEY_EXTRA
import com.bkalysh.devicer2.utils.JWT
import com.bkalysh.devicer2.utils.Utils.mapModelToImageResource
import com.bkalysh.devicer2.viewmodels.DeviceInfoViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DeviceActivity : AppCompatActivity() {
    private lateinit var currentDevice: Device
    private lateinit var binding: ActivityDeviceBinding
    private val viewModel: DeviceInfoViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDeviceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnExit.setOnClickListener {
            finish()
        }

        setUpToaster()
    }

    override fun onStart() {
        super.onStart()
        val deviceId = intent.getLongExtra(DEVICE_KEY_EXTRA, -1L)

        if (deviceId == -1L) {
            Toast.makeText(this, "Device not found", Toast.LENGTH_SHORT).show()
            finish()
        }

        viewModel.setUpCurrentDevice(deviceId)
        setupDeviceData()
        setupPowerButtons()
        setupOptionsButton()
        setupDeleteButton()
        setupDeletionObserver()
        setupDeviceControlFragment()
    }

    private fun setupDeviceData() {
        viewModel.currentDevice.observe(this) { device ->
            Log.e("dfs", "UPDATING device: $device" )

            device?.apply {
                currentDevice = this
                binding.tvDeviceName.text = name

                binding.tvSerial.text = serialNumber
                binding.tvMac.text = macAddress
                binding.tvFirmware.text = firmwareVersion

                binding.imgDevice.setImageResource(mapModelToImageResource(deviceModelId.toInt()))

                viewModel.deviceModels.observe(this@DeviceActivity) { models ->
                    binding.tvModel.text = (models.find { it.id == deviceModelId })?.name ?: "Unknown model"
                }

                viewModel.updateDeviceType(device)
            }
        }
    }

    private fun setupPowerButtons() {
        viewModel.currentDevice.observe(this) { device ->
            device?.let {
                binding.btnPowerOff.isVisible = device.isPowered
                binding.btnPowerOn.isVisible = !device.isPowered

                binding.btnPowerOff.setOnClickListener {
                    JWT.getJwtToken(this)?.let { token ->
                        viewModel.setPowerState(token, device, false)
                        binding.btnPowerOff.isVisible = false
                        binding.btnPowerOn.isVisible = true
                    }
                }

                binding.btnPowerOn.setOnClickListener {
                    JWT.getJwtToken(this)?.let { token ->
                        viewModel.setPowerState(token, device, true)
                        binding.btnPowerOff.isVisible = true
                        binding.btnPowerOn.isVisible = false
                    }
                }
            }
        }
    }

    private fun setUpToaster() {
        viewModel.toastMessage.observe(this) { text ->
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupOptionsButton() {
        binding.btnOptions.setOnClickListener {
            binding.dimmer.isVisible = true
            binding.btnDelete.isVisible = true
        }

        binding.dimmer.setOnClickListener {
            binding.dimmer.isVisible = false
            binding.btnDelete.isVisible = false
        }
    }

    private fun setupDeleteButton() {
        binding.btnDelete.setOnClickListener {
            JWT.getJwtToken(this)?.let { token ->
                viewModel.deleteDevice(token, currentDevice)
                binding.dimmer.isVisible = false
                binding.btnDelete.isVisible = false
            }
        }
    }

    private fun setupDeletionObserver() {
        viewModel.shouldFinish.observe(this) { shouldFinish ->
            if (shouldFinish) finish()
        }
    }

    private fun setupDeviceControlFragment() {
        viewModel.currentDeviceType.observe(this) { type ->
            type?.let {
                when (type.name) {
                    "Smart lamp" -> {
                        supportFragmentManager.beginTransaction().apply {
                            replace(R.id.fl_device_control_fragment, SmartLampControlFragment(viewModel.currentDevice.value, viewModel))
                            commit()
                        }
                    }
                    "Smart plug" -> {
                        supportFragmentManager.beginTransaction().apply {
                            replace(R.id.fl_device_control_fragment, SmartPlugControlFragment(viewModel.currentDevice.value, viewModel))
                            commit()
                        }
                    }
                    "Thermostat" -> {
                        supportFragmentManager.beginTransaction().apply {
                            replace(R.id.fl_device_control_fragment, ThermostatControlFragment(viewModel.currentDevice.value, viewModel))
                            commit()
                        }
                    }
                }
            }
        }
    }
}