package com.bkalysh.devicer2.mocked.backend.db.repository

import com.bkalysh.devicer2.mocked.backend.db.dao.DeviceTypeDao
import com.bkalysh.devicer2.mocked.backend.db.models.DeviceType
import kotlinx.coroutines.flow.Flow

class DeviceTypeRepository(private val deviceTypeDao: DeviceTypeDao) {

    suspend fun upsertDeviceType(deviceType: DeviceType) {
        deviceTypeDao.upsert(deviceType)
    }

    suspend fun deleteDeviceType(deviceType: DeviceType) {
        deviceTypeDao.delete(deviceType)
    }

    suspend fun getDeviceTypeById(id: Long): DeviceType? {
        return deviceTypeDao.getDeviceTypeById(id)
    }

    fun getAllDeviceTypes(): Flow<List<DeviceType>> {
        return deviceTypeDao.getAllDeviceTypes()
    }
}