package com.bkalysh.devicer2.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.bkalysh.devicer2.R
import com.bkalysh.devicer2.database.models.DeviceModel

class DeviceModelSpinnerAdapter(context: Context, private val deviceModels: List<DeviceModel>) :
    ArrayAdapter<DeviceModel>(context, android.R.layout.simple_spinner_item, deviceModels) {

    override fun getItem(position: Int): DeviceModel {
        return deviceModels[position]
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getDropDownView(position, convertView, parent)
        val textView = view.findViewById<TextView>(android.R.id.text1)
        textView.text = deviceModels[position].name
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
        textView.text = deviceModels[position].name
        textView.setPadding(32, 32, 32, 32)
        val textSize = parent.context.resources.getDimension(R.dimen.spinner_text_size)
        textView.textSize = textSize / parent.context.resources.displayMetrics.density
        val textColor = ContextCompat.getColor(parent.context, R.color.on_secondary)
        textView.setTextColor(textColor)
        return view
    }
}