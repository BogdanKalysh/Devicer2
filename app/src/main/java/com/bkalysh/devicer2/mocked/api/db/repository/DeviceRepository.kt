package com.bkalysh.devicer2.mocked.api.db.repository

import com.bkalysh.devicer2.mocked.api.db.dao.DeviceDao
import com.bkalysh.devicer2.mocked.api.db.models.Device
import com.bkalysh.devicer2.mocked.api.db.models.unique.fields.SmartLampData
import com.bkalysh.devicer2.mocked.api.db.models.unique.fields.SmartPlugData
import com.bkalysh.devicer2.mocked.api.db.models.unique.fields.ThermostatData

class DeviceRepository(private val deviceDao: DeviceDao) {

    // Device operations
    suspend fun insertDevice(device: Device) {
        deviceDao.insertDevice(device)
    }

    suspend fun updateDevicePowerState(id: Long, isPowered: Boolean) {
        deviceDao.updateDevicePowerState(id, isPowered)
    }

    fun getDeviceById(id: Long): Device? {
        return deviceDao.getDeviceById(id)
    }

    fun getDeviceBySerial(serialNumber: String): Device? {
        return deviceDao.getDeviceBySerial(serialNumber)
    }

    fun getDevicesByOwnerId(ownerId: Long): List<Device> {
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
