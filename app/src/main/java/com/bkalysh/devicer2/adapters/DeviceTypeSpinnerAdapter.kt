package com.bkalysh.devicer2.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.bkalysh.devicer2.R
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
        textView.setPadding(32, 32, 32, 32)
        val textSize = parent.context.resources.getDimension(R.dimen.spinner_text_size)
        textView.textSize = textSize / parent.context.resources.displayMetrics.density
        val textColor = ContextCompat.getColor(parent.context, R.color.on_secondary)
        textView.setTextColor(textColor)
        return view
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)
        val textView = view.findViewById<TextView>(android.R.id.text1)
        textView.text = deviceTypes[position].name
        textView.setPadding(32, 32, 32, 32)
        val textSize = parent.context.resources.getDimension(R.dimen.spinner_text_size)
        textView.textSize = textSize / parent.context.resources.displayMetrics.density
        val textColor = ContextCompat.getColor(parent.context, R.color.on_secondary)
        textView.setTextColor(textColor)
        return view
    }
}