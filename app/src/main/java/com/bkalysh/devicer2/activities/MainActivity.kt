package com.bkalysh.devicer2.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.bkalysh.devicer2.R
import com.bkalysh.devicer2.viewmodels.MainViewModel
import com.bkalysh.devicer2.databinding.ActivityMainBinding
import com.bkalysh.devicer2.fragments.AddDeviceDialogFragment
import com.bkalysh.devicer2.utils.Constants.ADD_DEVICE_DIALOG
import com.bkalysh.devicer2.utils.JWT.Companion.deleteJwtToken
import com.bkalysh.devicer2.utils.JWT.Companion.getJwtToken
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupOnClickListeners()
        setupLogoutListener()
        updateDataFromAPI()
    }

    private fun setupOnClickListeners() {
        binding.clUserData.setOnClickListener {
            binding.btnLogout.isVisible = !binding.btnLogout.isVisible
        }

        binding.btnLogout.setOnClickListener {
            logout()
        }

        binding.btnAddDevice.setOnClickListener {
            AddDeviceDialogFragment().show(supportFragmentManager, ADD_DEVICE_DIALOG)
        }
    }

    private fun updateDataFromAPI() {
        getJwtToken(this)?.let { token ->
            viewModel.updateUserName(token)
            viewModel.fetchDevicesData()
        }

        viewModel.userName.observe(this) { userName ->
            binding.tvUserName.text = userName
        }
    }

    private fun setupLogoutListener() {
        viewModel.shouldLogOut.observe(this) { shouldLogOut ->
            if (shouldLogOut) logout()
        }
    }

    private fun logout() {
        deleteJwtToken(this)
        Toast.makeText(this, "Logging out", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, StartActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}