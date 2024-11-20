package com.bkalysh.devicer2.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bkalysh.devicer2.R
import com.bkalysh.devicer2.activity.viewmodel.SignUpViewmodel
import com.bkalysh.devicer2.databinding.ActivitySignUpBinding
import com.bkalysh.devicer2.utils.JWT
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private val viewModel: SignUpViewmodel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupClickListeners()
        setupSignUpObserver()
    }

    private fun setupClickListeners() {
        binding.btnLogInScreen.setOnClickListener {
            val intent = Intent(this, LogInActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnSignUp.setOnClickListener {
            val email = binding.etEmail.text.trim().toString()
            val name = binding.etName.text.trim().toString()
            val password = binding.etPassword.text.toString()

            viewModel.processSignUp(email, name, password)
        }
    }

    private fun setupSignUpObserver() {
        viewModel.signUpResult.observe(this) { result ->
            result.onSuccess { jwtToken ->
                JWT.storeJwtToken(jwtToken, this)
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }.onFailure { exception ->
                Toast.makeText(this, exception.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}