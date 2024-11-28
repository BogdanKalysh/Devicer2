package com.bkalysh.devicer2.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bkalysh.devicer2.database.dao.DeviceDao
import com.bkalysh.devicer2.database.dao.DeviceModelDao
import com.bkalysh.devicer2.database.dao.DeviceTypeDao
import com.bkalysh.devicer2.database.models.Device
import com.bkalysh.devicer2.database.models.DeviceModel
import com.bkalysh.devicer2.database.models.DeviceType

@Database(
    entities = [
        DeviceModel::class,
        DeviceType::class,
        Device::class
    ],
    version = 1,
    exportSchema = false
)
abstract class DevicerDatabase: RoomDatabase()  {
    abstract val deviceTypeDao: DeviceTypeDao
    abstract val deviceModelDao: DeviceModelDao
    abstract val deviceDao: DeviceDao
}