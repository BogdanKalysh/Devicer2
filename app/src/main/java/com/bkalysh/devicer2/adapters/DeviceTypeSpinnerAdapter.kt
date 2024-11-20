package com.bkalysh.devicer2.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.bkalysh.devicer2.database.models.DeviceType

class DeviceTypeSpinnerAdapter(context: Context, private val deviceTypes: List<DeviceType>) :
    ArrayAdapter<DeviceType>(context, android.R.layout.simple_spinner_item, deviceTypes) {

    override fun getItem(position: Int): DeviceType {
        return deviceTypes[position]
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getDropDownView(position, convertView, parent)
        val textView = view.findViewById<TextView>(android.R.id.text1)
        textView.text = deviceTypes[position].name
        return view
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)
        val textView = view.findViewById<TextView>(android.R.id.text1)

        // Set the selected type text (this is shown when the spinner is closed)
        textView.text = deviceTypes[position].name

        // Optionally customize the appearance of the selected item
        // For example, change text color, add padding, etc.
        textView.setPadding(32, 32, 32, 32)  // Customize padding if needed

        return view
    }
}