package com.bkalysh.devicer2.mocked.backend.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Delete
import androidx.room.Upsert
import com.bkalysh.devicer2.mocked.backend.db.models.DeviceModel
import kotlinx.coroutines.flow.Flow

@Dao
interface DeviceModelDao {

    @Upsert
    suspend fun upsert(deviceModel: DeviceModel)

    @Delete
    suspend fun delete(deviceModel: DeviceModel)

    @Query("SELECT * FROM device_model WHERE id = :id")
    suspend fun getDeviceModelById(id: Long): DeviceModel?

    @Query("SELECT * FROM device_model")
    fun getAllDeviceModels(): Flow<List<DeviceModel>>
}
