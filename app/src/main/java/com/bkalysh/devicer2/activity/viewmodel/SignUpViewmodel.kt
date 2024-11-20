package com.bkalysh.devicer2.activity.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.bkalysh.devicer2.ServerAPI

class SignUpViewmodel(private val api: ServerAPI): ViewModel() {
    private val _signUpResult = MutableLiveData<Result<String>>()
    val signUpResult: LiveData<Result<String>> get() = _signUpResult

    fun processSignUp(email: String, password: String, name: String) {
        viewModelScope.launch {
            try {
                val jwtToken = api.registerUser(email, password, name)
                _signUpResult.postValue(Result.success(jwtToken))
            } catch (e: Exception) {
                _signUpResult.postValue(Result.failure(e))
            }
        }
    }
}