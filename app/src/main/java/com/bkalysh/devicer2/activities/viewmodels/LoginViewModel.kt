package com.bkalysh.devicer2.activities.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.bkalysh.devicer2.ServerAPI

class LoginViewModel(private val api: ServerAPI): ViewModel() {
    private val _loginResult = MutableLiveData<Result<String>>()
    val loginResult: LiveData<Result<String>> get() = _loginResult

    fun processLogin(email: String, password: String) {
        viewModelScope.launch {
            try {
                val jwtToken = api.loginUser(email, password)
                _loginResult.postValue(Result.success(jwtToken))
            } catch (e: Exception) {
                _loginResult.postValue(Result.failure(e))
            }
        }
    }
}