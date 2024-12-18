package com.bkalysh.devicer2.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Insert
import com.bkalysh.devicer2.database.models.DeviceType

@Dao
interface DeviceTypeDao {

    @Insert
    suspend fun insertAll(deviceTypes: List<DeviceType>)

    @Query("DELETE FROM device_type")
    suspend fun deleteAll()

    @Query("SELECT * FROM device_type WHERE id = :id")
    fun getById(id: Long): DeviceType

    @Query("SELECT * FROM device_type")
    fun getAll(): LiveData<List<DeviceType>>
}
