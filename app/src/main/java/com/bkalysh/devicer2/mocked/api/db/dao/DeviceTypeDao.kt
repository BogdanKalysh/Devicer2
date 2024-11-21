package com.bkalysh.devicer2.mocked.api.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Delete
import androidx.room.Insert
import com.bkalysh.devicer2.mocked.api.db.models.DeviceType

@Dao
interface DeviceTypeDao {

    @Insert
    suspend fun insert(deviceType: DeviceType)

    @Insert
    suspend fun insertAll(deviceTypes: List<DeviceType>)

    @Delete
    suspend fun delete(deviceType: DeviceType)

    @Query("SELECT * FROM device_type WHERE id = :id")
    suspend fun getDeviceTypeById(id: Long): DeviceType?

    @Query("SELECT * FROM device_type")
    suspend fun getAllDeviceTypes(): List<DeviceType>
}
