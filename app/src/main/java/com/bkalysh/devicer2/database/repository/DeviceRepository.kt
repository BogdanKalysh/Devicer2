package com.bkalysh.devicer2.database.repository

import androidx.lifecycle.LiveData
import com.bkalysh.devicer2.database.dao.DeviceDao
import com.bkalysh.devicer2.database.models.Device

class DeviceRepository(private val deviceDao: DeviceDao) {
    suspend fun insertDevice(device: Device) {
        deviceDao.insertDevice(device)
    }

    suspend fun updateDevicePowerState(id: Long, isPowered: Boolean) {
        deviceDao.updateDevicePowerState(id, isPowered)
    }

    suspend fun insertAllDevices(devices: List<Device>) {
        deviceDao.insertAllDevices(devices)
    }

    suspend fun deleteAllDevices() {
        deviceDao.deleteAllDevices()
    }

    fun getAllDevices(): LiveData<List<Device>> {
        return deviceDao.getAllDevices()
    }

    suspend fun deleteDevice(device: Device) {
        deviceDao.deleteDevice(device)
    }
}
