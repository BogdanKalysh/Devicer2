package com.bkalysh.devicer2.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bkalysh.devicer2.ServerAPI
import com.bkalysh.devicer2.database.models.Device
import com.bkalysh.devicer2.database.models.DeviceModel
import com.bkalysh.devicer2.database.models.DeviceType
import com.bkalysh.devicer2.database.repository.DevicerRepositoryFacade
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DeviceInfoViewModel(private val api: ServerAPI, private val repositoryFacade: DevicerRepositoryFacade): ViewModel() {
    private lateinit var _currentDevice: LiveData<Device>
    val currentDevice: LiveData<Device> get() = _currentDevice
    val deviceModels: LiveData<List<DeviceModel>> = repositoryFacade.deviceModelRepository.getAllDeviceModels()

    private val _currentDeviceType: MutableLiveData<DeviceType?> = MutableLiveData()
    val currentDeviceType: LiveData<DeviceType?> get() = _currentDeviceType


    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String> get() = _toastMessage
    private val _shouldFinish = MutableLiveData(false)
    val shouldFinish: LiveData<Boolean> get() = _shouldFinish

    private val gson = Gson()
    private val scope = CoroutineScope(Dispatchers.IO)

    fun setUpCurrentDevice (deviceId: Long) {
        _currentDevice = repositoryFacade.deviceRepository.getDeviceById(deviceId)
    }

    fun updateDeviceType(device: Device) {
        scope.launch {
            val deviceModel = getDeviceModelById(device.deviceModelId)
            val deviceType = getDeviceTypeById(deviceModel.deviceTypeId)

            _currentDeviceType.postValue(deviceType)
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

    fun deleteDevice(jwtToken: String, device: Device) {
        scope.launch {
            val deviceJson = gson.toJson(device)
            api.deleteDevice(jwtToken, deviceJson)
                .onSuccess {
                    _shouldFinish.postValue(true)
                }.onFailure {
                    _toastMessage.postValue("Unable to delete the device")
                }
        }
    }

    private fun getDeviceModelById(id: Long): DeviceModel {
        return repositoryFacade.deviceModelRepository.getDeviceModelById(id)
    }

    private fun getDeviceTypeById(id: Long): DeviceType {
        return repositoryFacade.deviceTypeRepository.getDeviceTypeById(id)
    }
}