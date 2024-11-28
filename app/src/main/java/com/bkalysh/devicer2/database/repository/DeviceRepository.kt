package com.bkalysh.devicer2.database.repository

import com.bkalysh.devicer2.database.dao.DeviceDao
import com.bkalysh.devicer2.database.models.Device
import kotlinx.coroutines.flow.Flow

class DeviceRepository(private val deviceDao: DeviceDao) {
    suspend fun insertDevice(device: Device) {
        deviceDao.insertDevice(device)
    }

    fun getDeviceById(id: Long): Device? {
        return deviceDao.getDeviceById(id)
    }

    fun getDeviceBySerial(serialNumber: String): Device? {
        return deviceDao.getDeviceBySerial(serialNumber)
    }

    fun getDevicesByOwnerId(ownerId: Long): Flow<List<Device>> {
        return deviceDao.getDevicesByOwnerId(ownerId)
    }

    suspend fun deleteDevice(device: Device) {
        deviceDao.deleteDevice(device)
    }
}
