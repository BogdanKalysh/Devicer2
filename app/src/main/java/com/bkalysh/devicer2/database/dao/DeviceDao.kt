package com.bkalysh.devicer2.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Delete
import androidx.room.Insert
import com.bkalysh.devicer2.database.models.Device

@Dao
interface DeviceDao {
    @Insert
    suspend fun insertDevice(device: Device)

    @Query("UPDATE device SET is_powered = :isPowered WHERE id = :id")
    suspend fun updateDevicePowerState(id: Long, isPowered: Boolean)

    @Insert
    suspend fun insertAllDevices(devices: List<Device>)

    @Query("SELECT * FROM device WHERE id = :id")
    fun getDeviceById(id: Long): LiveData<Device>

    @Query("SELECT * FROM device WHERE serial_number = :serialNumber")
    suspend fun getDeviceBySerial(serialNumber: String): Device?

    @Query("SELECT * FROM device")
    fun getAllDevices(): LiveData<List<Device>>

    @Query("DELETE FROM device")
    suspend fun deleteAllDevices()

    @Delete
    suspend fun deleteDevice(device: Device)
}
