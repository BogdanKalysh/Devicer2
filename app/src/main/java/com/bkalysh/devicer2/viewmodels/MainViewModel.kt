package com.bkalysh.devicer2.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bkalysh.devicer2.ServerAPI
import com.bkalysh.devicer2.database.models.Device
import com.bkalysh.devicer2.database.models.DeviceModel
import com.bkalysh.devicer2.database.models.DeviceType
import com.bkalysh.devicer2.database.repository.DevicerRepositoryFacade
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel(private val api: ServerAPI, private val repositoryFacade: DevicerRepositoryFacade): ViewModel() {
    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> get() = _userName
    private val _shouldLogOut = MutableLiveData(false)
    val shouldLogOut: LiveData<Boolean> get() = _shouldLogOut
    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String> get() = _toastMessage

    val deviceTypes: LiveData<List<DeviceType>> = repositoryFacade.deviceTypeRepository.getAllDeviceTypes()
    val deviceModels: LiveData<List<DeviceModel>> = repositoryFacade.deviceModelRepository.getAllDeviceModels()
    val devices: LiveData<List<Device>> = repositoryFacade.deviceRepository.getAllDevices()

    private val gson = Gson()
    private val scope = CoroutineScope(Dispatchers.IO)

    fun fetchDevicesData(jwtToken: String) {
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

        scope.launch {
            try {
                val jsonDevices = api.getAllDevices(jwtToken)
                val devicesToken = object : TypeToken<List<Device>>() {}.type
                val devices: List<Device> = gson.fromJson(jsonDevices, devicesToken)

                repositoryFacade.deviceRepository.deleteAllDevices()
                repositoryFacade.deviceRepository.insertAllDevices(devices)
            } catch (e: Exception) {
                _shouldLogOut.postValue(true)
            }
        }
    }

    fun fetchUserName(jwtToken: String) {
        scope.launch {
            try {
                val name = api.getUserName(jwtToken)
                _userName.postValue(name)
            } catch (e: Exception) {
                _shouldLogOut.postValue(true)
            }
        }
    }

    fun addDevice(jwtToken: String, device: Device) {
        scope.launch {
            api.addDevice(jwtToken,  gson.toJson(device)).onSuccess { deviceJson ->
                val deviceObj = gson.fromJson(deviceJson, Device::class.java)
                repositoryFacade.deviceRepository.insertDevice(deviceObj)
            }.onFailure {
                _toastMessage.postValue("Unable to create a device due to an issue")
            }
        }
    }

    fun dropLocalDatabase() {
        scope.launch {
            repositoryFacade.deviceRepository.deleteAllDevices()
        }
    }

    fun setPowerState(jwtToken: String, oldDevice: Device, isPowered: Boolean) {
        scope.launch {
            val switchedDevice = oldDevice.copy(isPowered = isPowered)
            api.updateDevicePowerState(jwtToken, gson.toJson(switchedDevice))
                .onSuccess { newDeviceJson ->
                    val newDevice = gson.fromJson(newDeviceJson, Device::class.java)
                    repositoryFacade.deviceRepository.updateDevicePowerState(newDevice.id, newDevice.isPowered)
                }
                .onFailure {
                    _toastMessage.postValue("Unable to switch power state")
                    if (oldDevice.isPowered != isPowered) {
                        repositoryFacade.deviceRepository.updateDevicePowerState(
                            oldDevice.id,
                            !oldDevice.isPowered
                        )
                        delay(200)
                        repositoryFacade.deviceRepository.updateDevicePowerState(
                            oldDevice.id,
                            oldDevice.isPowered
                        )
                    }
                }
        }
    }
}