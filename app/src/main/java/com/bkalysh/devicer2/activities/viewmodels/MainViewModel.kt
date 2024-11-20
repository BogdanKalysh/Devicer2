package com.bkalysh.devicer2.activities.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bkalysh.devicer2.ServerAPI
import com.bkalysh.devicer2.database.models.DeviceModel
import com.bkalysh.devicer2.database.models.DeviceType
import com.bkalysh.devicer2.database.repository.DevicerRepositoryFacade
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val api: ServerAPI, private val repositoryFacade: DevicerRepositoryFacade): ViewModel() {
    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> get() = _userName
    private val _shouldLogOut = MutableLiveData(false)
    val shouldLogOut: LiveData<Boolean> get() = _shouldLogOut

    val deviceTypes: LiveData<List<DeviceType>> = repositoryFacade.deviceTypeRepository.getAllDeviceTypes()
    val deviceModels: LiveData<List<DeviceModel>> = repositoryFacade.deviceModelRepository.getAllDeviceModels()

    private val gson = Gson()
    private val scope = CoroutineScope(Dispatchers.IO)

    fun fetchDevicesData() {
        scope.launch {
            val jsonDeviceTypes = api.getDeviceTypes()
            val deviceTypesTypeToken = object : TypeToken<List<DeviceType>>() {}.type
            val deviceTypes: List<DeviceType> = gson.fromJson(jsonDeviceTypes, deviceTypesTypeToken)

            repositoryFacade.deviceTypeRepository.deleteAllDeviceTypes()
            repositoryFacade.deviceTypeRepository.insertAllDeviceTypes(deviceTypes)

            val jsonDeviceModels = api.getDeviceModels()
            val deviceModelsTypeToken = object : TypeToken<List<DeviceModel>>() {}.type
            val deviceModels: List<DeviceModel> = gson.fromJson(jsonDeviceModels, deviceModelsTypeToken)

            repositoryFacade.deviceModelRepository.deleteAllDeviceModels()
            repositoryFacade.deviceModelRepository.insertAllDeviceModels(deviceModels)
        }
    }

    fun updateUserName(jwtToken: String) {
        scope.launch {
            try {
                val name = api.getUserName(jwtToken)
                _userName.postValue(name)
            } catch (e: Exception) {
                _shouldLogOut.postValue(true)
            }
        }
    }
}