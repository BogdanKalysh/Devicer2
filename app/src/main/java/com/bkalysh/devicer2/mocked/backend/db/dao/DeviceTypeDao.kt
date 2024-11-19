package com.bkalysh.devicer2.mocked.backend.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Delete
import androidx.room.Upsert
import com.bkalysh.devicer2.mocked.backend.db.model.DeviceType
import kotlinx.coroutines.flow.Flow

@Dao
interface DeviceTypeDao {

    @Upsert
    suspend fun upsert(deviceType: DeviceType)

    @Delete
    suspend fun delete(deviceType: DeviceType)

    @Query("SELECT * FROM device_type WHERE id = :id")
    suspend fun getDeviceTypeById(id: Long): DeviceType?

    @Query("SELECT * FROM device_type")
    fun getAllDeviceTypes(): Flow<List<DeviceType>>
}
