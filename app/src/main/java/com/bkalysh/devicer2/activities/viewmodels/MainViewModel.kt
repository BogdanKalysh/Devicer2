package com.bkalysh.devicer2.activities.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bkalysh.devicer2.ServerAPI
import kotlinx.coroutines.launch

class MainViewModel(private val api: ServerAPI): ViewModel() {
    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> get() = _userName

    private val _shouldLogOut = MutableLiveData(false)
    val shouldLogOut: LiveData<Boolean> = _shouldLogOut

    fun updateUserName(jwtToken: String) {
        viewModelScope.launch {
            try {
                val name = api.getUserName(jwtToken)
                _userName.postValue(name)
            } catch (e: Exception) {
                _shouldLogOut.postValue(true)
            }
        }
    }
}