package com.bkalysh.devicer2.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Delete
import androidx.room.Insert
import com.bkalysh.devicer2.database.models.Device
import kotlinx.coroutines.flow.Flow

@Dao
interface DeviceDao {
    @Insert
    suspend fun insertDevice(device: Device)

    @Query("SELECT * FROM device WHERE id = :id")
    fun getDeviceById(id: Long): Device?

    @Query("SELECT * FROM device WHERE serial_number = :serialNumber")
    fun getDeviceBySerial(serialNumber: String): Device?

    @Query("SELECT * FROM device WHERE owner_id = :ownerId")
    fun getDevicesByOwnerId(ownerId: Long): Flow<List<Device>>

    @Delete
    suspend fun deleteDevice(device: Device)
}
