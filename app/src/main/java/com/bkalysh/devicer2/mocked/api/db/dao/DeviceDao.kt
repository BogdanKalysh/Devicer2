package com.bkalysh.devicer2.mocked.api.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Upsert
import com.bkalysh.devicer2.mocked.api.db.models.Device
import com.bkalysh.devicer2.mocked.api.db.models.unique.fields.SmartLampData
import com.bkalysh.devicer2.mocked.api.db.models.unique.fields.SmartPlugData
import com.bkalysh.devicer2.mocked.api.db.models.unique.fields.ThermostatData

@Dao
interface DeviceDao {
    @Insert
    suspend fun insertDevice(device: Device)

    @Query("UPDATE device SET is_powered = :isPowered WHERE id = :id")
    suspend fun updateDevicePowerState(id: Long, isPowered: Boolean)

    @Query("SELECT * FROM device WHERE id = :id")
    fun getDeviceById(id: Long): Device?

    @Query("SELECT * FROM device WHERE serial_number = :serialNumber")
    fun getDeviceBySerial(serialNumber: String): Device?

    @Query("SELECT * FROM device WHERE owner_id = :ownerId")
    fun getDevicesByOwnerId(ownerId: Long): List<Device>

    @Delete
    suspend fun deleteDevice(device: Device)


    // Smart lamp specific data db queries
    @Upsert
    suspend fun upsertLampData(smartLampData: SmartLampData)

    @Query("SELECT * FROM smart_lamp_data WHERE device_id = :deviceId")
    suspend fun getLampDataByDeviceId(deviceId: Long): SmartLampData?

    @Delete
    suspend fun deleteLampData(smartLampData: SmartLampData)


    // Smart plug specific data db queries
    @Upsert
    suspend fun upsertPlugData(smartPlugData: SmartPlugData)

    @Query("SELECT * FROM smart_plug_data WHERE device_id = :deviceId")
    suspend fun getPlugDataByDeviceId(deviceId: Long): SmartPlugData?

    @Delete
    suspend fun deletePlugData(smartLampData: SmartPlugData)


    // Thermostat plug specific data db queries
    @Upsert
    suspend fun upsertThermostatData(thermostatData: ThermostatData)

    @Query("SELECT * FROM thermostat_data WHERE device_id = :deviceId")
    suspend fun getThermostatDataByDeviceId(deviceId: Long): ThermostatData?

    @Delete
    suspend fun deleteThermostatData(smartLampData: ThermostatData)

}
