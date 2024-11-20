package com.bkalysh.devicer2.mocked.backend.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Delete
import androidx.room.Insert
import com.bkalysh.devicer2.mocked.backend.db.models.DeviceModel
import kotlinx.coroutines.flow.Flow

@Dao
interface DeviceModelDao {

    @Insert
    suspend fun insert(deviceModel: DeviceModel)

    @Insert
    suspend fun insertAll(deviceModels: List<DeviceModel>)

    @Delete
    suspend fun delete(deviceModel: DeviceModel)

    @Query("SELECT * FROM device_model WHERE id = :id")
    suspend fun getDeviceModelById(id: Long): DeviceModel?

    @Query("SELECT * FROM device_model")
    fun getAllDeviceModels(): Flow<List<DeviceModel>>
}
