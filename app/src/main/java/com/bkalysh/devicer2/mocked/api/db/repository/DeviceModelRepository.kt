package com.bkalysh.devicer2.mocked.api.db.repository

import com.bkalysh.devicer2.mocked.api.db.dao.DeviceModelDao
import com.bkalysh.devicer2.mocked.api.db.models.DeviceModel

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

    suspend fun getAllDeviceModels(): List<DeviceModel> {
        return deviceModelDao.getAllDeviceModels()
    }
}