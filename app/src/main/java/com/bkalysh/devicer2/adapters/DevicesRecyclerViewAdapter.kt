package com.bkalysh.devicer2.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bkalysh.devicer2.activities.DeviceActivity
import com.bkalysh.devicer2.R
import com.bkalysh.devicer2.database.models.Device
import com.bkalysh.devicer2.database.models.DeviceModel
import com.bkalysh.devicer2.databinding.ItemDeviceBinding
import com.bkalysh.devicer2.utils.Utils.mapModelToImageResource

class DevicesRecyclerViewAdapter(private val context: Context) : RecyclerView.Adapter<DevicesRecyclerViewAdapter.DeviceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
        Log.d(TAG, "Created DevicesAdapter")
        return DeviceViewHolder(
            ItemDeviceBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return devices.size
    }

    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        holder.binding.apply {
            val device = devices[position]
            Log.d(TAG, "Binding device: $device")
            tvDeviceName.text = device.name
            tvDeviceSerial.text = context.getString(R.string.device_serial, device.serialNumber)
            Log.e("BTAG", "ID: " + device.deviceModelId)
            tvDeviceModel.text = (deviceModels.find { it.id == device.deviceModelId })?.name
                ?: context.getString(R.string.undefined_model)

            imgDevice.setImageResource(mapModelToImageResource(device.deviceModelId.toInt()))

            swPowerState.isChecked = device.isPowered

            root.setOnClickListener {
                Log.i(TAG, "Starting device info activity for: $device")
                val intent = Intent(context, DeviceActivity::class.java)
                context.startActivity(intent)
            }

            swPowerState.setOnClickListener {
                Log.i(TAG, "Sending request to update device power state for: $device")

            }
        }
    }

    inner class DeviceViewHolder(val binding: ItemDeviceBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<Device>() {
        override fun areItemsTheSame(oldItem: Device, newItem: Device): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Device, newItem: Device): Boolean {
            return oldItem == newItem
        }
    }
    private val differ = AsyncListDiffer(this, diffCallback)
    var devices: List<Device>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    var deviceModels: List<DeviceModel> = listOf()
        set(value) {
            field = value
            if (devices.isNotEmpty()) differ.submitList(devices)
        }

    companion object {
        val TAG: String = DevicesRecyclerViewAdapter::class.java.name
    }
}