package com.bkalysh.devicer2.mocked.api.db.repository

import com.bkalysh.devicer2.mocked.api.db.dao.DeviceTypeDao
import com.bkalysh.devicer2.mocked.api.db.models.DeviceType

class DeviceTypeRepository(private val deviceTypeDao: DeviceTypeDao) {

    suspend fun insertDeviceType(deviceType: DeviceType) {
        deviceTypeDao.insert(deviceType)
    }

    suspend fun insertAllDeviceTypes(deviceTypes: List<DeviceType>) {
        deviceTypeDao.insertAll(deviceTypes)
    }

    suspend fun deleteDeviceType(deviceType: DeviceType) {
        deviceTypeDao.delete(deviceType)
    }

    suspend fun getDeviceTypeById(id: Long): DeviceType? {
        return deviceTypeDao.getDeviceTypeById(id)
    }

    suspend fun getAllDeviceTypes(): List<DeviceType> {
        return deviceTypeDao.getAllDeviceTypes()
    }
}