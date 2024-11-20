package com.bkalysh.devicer2.mocked.backend.db.repository

import com.bkalysh.devicer2.mocked.backend.db.dao.DeviceDao
import com.bkalysh.devicer2.mocked.backend.db.models.Device
import com.bkalysh.devicer2.mocked.backend.db.models.unique.fields.SmartLampData
import com.bkalysh.devicer2.mocked.backend.db.models.unique.fields.SmartPlugData
import com.bkalysh.devicer2.mocked.backend.db.models.unique.fields.ThermostatData
import kotlinx.coroutines.flow.Flow

class DeviceRepository(private val deviceDao: DeviceDao) {

    // Device operations
    suspend fun upsertDevice(device: Device) {
        deviceDao.upsertDevice(device)
    }

    fun getDeviceById(id: Long): Flow<Device?> {
        return deviceDao.getDeviceById(id)
    }

    fun getDevicesByOwnerId(ownerId: Long): Flow<List<Device>> {
        return deviceDao.getDevicesByOwnerId(ownerId)
    }

    suspend fun deleteDevice(device: Device) {
        deviceDao.deleteDevice(device)
    }


    // SmartLampData-specific operations
    suspend fun upsertLampData(smartLampData: SmartLampData) {
        deviceDao.upsertLampData(smartLampData)
    }

    suspend fun getLampDataByDeviceId(deviceId: Long): SmartLampData? {
        return deviceDao.getLampDataByDeviceId(deviceId)
    }

    suspend fun deleteLampData(smartLampData: SmartLampData) {
        deviceDao.deleteLampData(smartLampData)
    }


    // SmartPlugData-specific operations
    suspend fun upsertPlugData(smartPlugData: SmartPlugData) {
        deviceDao.upsertPlugData(smartPlugData)
    }

    suspend fun getPlugDataByDeviceId(deviceId: Long): SmartPlugData? {
        return deviceDao.getPlugDataByDeviceId(deviceId)
    }

    suspend fun deletePlugData(smartPlugData: SmartPlugData) {
        deviceDao.deletePlugData(smartPlugData)
    }


    // ThermostatData-specific operations
    suspend fun upsertThermostatData(thermostatData: ThermostatData) {
        deviceDao.upsertThermostatData(thermostatData)
    }

    suspend fun getThermostatDataByDeviceId(deviceId: Long): ThermostatData? {
        return deviceDao.getThermostatDataByDeviceId(deviceId)
    }

    suspend fun deleteThermostatData(thermostatData: ThermostatData) {
        deviceDao.deleteThermostatData(thermostatData)
    }
}
