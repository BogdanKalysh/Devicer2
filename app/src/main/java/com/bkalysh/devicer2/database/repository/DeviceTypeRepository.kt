package com.bkalysh.devicer2.database.repository

import androidx.lifecycle.LiveData
import com.bkalysh.devicer2.database.dao.DeviceTypeDao
import com.bkalysh.devicer2.database.models.DeviceType


class DeviceTypeRepository(private val deviceTypeDao: DeviceTypeDao) {
    suspend fun insertAllDeviceTypes(deviceTypes: List<DeviceType>) {
        deviceTypeDao.insertAll(deviceTypes)
    }

    suspend fun deleteAllDeviceTypes() {
        deviceTypeDao.deleteAll()
    }

    fun getAllDeviceTypes(): LiveData<List<DeviceType>> {
        return deviceTypeDao.getAll()
    }
}