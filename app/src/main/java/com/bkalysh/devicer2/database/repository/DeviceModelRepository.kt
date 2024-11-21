package com.bkalysh.devicer2.database.repository

import androidx.lifecycle.LiveData
import com.bkalysh.devicer2.database.dao.DeviceModelDao
import com.bkalysh.devicer2.database.models.DeviceModel

class DeviceModelRepository(private val deviceModelDao: DeviceModelDao) {
    fun insertAllDeviceModels(deviceModels: List<DeviceModel>) {
        deviceModelDao.insertAll(deviceModels)
    }

    fun deleteAllDeviceModels() {
        deviceModelDao.deleteAll()
    }

    fun getAllDeviceModels(): LiveData<List<DeviceModel>> {
        return deviceModelDao.getAll()
    }
}