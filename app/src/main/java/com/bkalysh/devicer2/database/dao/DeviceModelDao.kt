package com.bkalysh.devicer2.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Insert
import com.bkalysh.devicer2.database.models.DeviceModel

@Dao
interface DeviceModelDao {

    @Insert
    suspend fun insertAll(deviceModels: List<DeviceModel>)

    @Query("DELETE FROM device_model")
    suspend fun deleteAll()

    @Query("SELECT * FROM device_model")
    fun getAll(): LiveData<List<DeviceModel>>
}
