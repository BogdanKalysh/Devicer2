package com.bkalysh.devicer2.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.DialogFragment
import com.bkalysh.devicer2.viewmodels.MainViewModel
import com.bkalysh.devicer2.adapters.DeviceModelSpinnerAdapter
import com.bkalysh.devicer2.adapters.DeviceTypeSpinnerAdapter
import com.bkalysh.devicer2.database.models.DeviceType
import com.bkalysh.devicer2.databinding.DialogFragmentAddDeviceBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddDeviceDialogFragment : DialogFragment()  {
    private lateinit var binding: DialogFragmentAddDeviceBinding
    private val viewModel: MainViewModel by viewModel()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            binding = DialogFragmentAddDeviceBinding.inflate(requireActivity().layoutInflater)
            builder.setView(binding.root)

            setupOnClickListeners()
            setUpDeviceSpinners()

            val dialog = builder.create()
            dialog
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    private fun setupOnClickListeners() {
        binding.btnCancelAdding.setOnClickListener {
            dismiss()
        }
        binding.btnAddDevice.setOnClickListener {
            // Add a new device
        }
    }


    private fun setUpDeviceSpinners() {
        val typesSpinner = binding.spDeviceTypes
        val modelsSpinner = binding.spDeviceModels

        // Observe device types list
        viewModel.deviceTypes.observe(this) { types ->
            val adapter = DeviceTypeSpinnerAdapter(requireContext(), types)
            typesSpinner.adapter = adapter
        }

        // Observe device models list and filter models according to current chosen device type
        viewModel.deviceModels.observe(this) { models ->
            typesSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                    val selectedDeviceType = parent.getItemAtPosition(position) as DeviceType
                    val deviceTypeId = selectedDeviceType.id

                    val filteredModels = models.filter { it.deviceTypeId == deviceTypeId }

                    val adapter = DeviceModelSpinnerAdapter(requireContext(), filteredModels)
                    modelsSpinner.adapter = adapter
                }
                override fun onNothingSelected(parent: AdapterView<*>) {}
            }
        }
    }
}