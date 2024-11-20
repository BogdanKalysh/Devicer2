package com.bkalysh.devicer2.mocked.backend.db.repository

import com.bkalysh.devicer2.mocked.backend.db.dao.DeviceModelDao
import com.bkalysh.devicer2.mocked.backend.db.models.DeviceModel
import kotlinx.coroutines.flow.Flow

class DeviceModelRepository(private val deviceModelDao: DeviceModelDao) {

    suspend fun insertDeviceModel(deviceModel: DeviceModel) {
        deviceModelDao.insert(deviceModel)
    }

    suspend fun insertAllDeviceModels(deviceModels: List<DeviceModel>) {
        deviceModelDao.insertAll(deviceModels)
    }

    suspend fun deleteDeviceModel(deviceModel: DeviceModel) {
        deviceModelDao.delete(deviceModel)
    }

    suspend fun getDeviceModelById(id: Long): DeviceModel? {
        return deviceModelDao.getDeviceModelById(id)
    }

    fun getAllDeviceModels(): Flow<List<DeviceModel>> {
        return deviceModelDao.getAllDeviceModels()
    }
}