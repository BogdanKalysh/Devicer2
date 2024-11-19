package com.bkalysh.devicer2.mocked.backend.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bkalysh.devicer2.mocked.backend.db.dao.DeviceDao
import com.bkalysh.devicer2.mocked.backend.db.dao.DeviceModelDao
import com.bkalysh.devicer2.mocked.backend.db.dao.DeviceTypeDao
import com.bkalysh.devicer2.mocked.backend.db.dao.UserDao
import com.bkalysh.devicer2.mocked.backend.db.model.Device
import com.bkalysh.devicer2.mocked.backend.db.model.DeviceModel
import com.bkalysh.devicer2.mocked.backend.db.model.DeviceType
import com.bkalysh.devicer2.mocked.backend.db.model.User
import com.bkalysh.devicer2.mocked.backend.db.model.unique.fields.SmartLampData
import com.bkalysh.devicer2.mocked.backend.db.model.unique.fields.SmartPlugData
import com.bkalysh.devicer2.mocked.backend.db.model.unique.fields.ThermostatData

@Database(
    entities = [
        Device::class,
        DeviceModel::class,
        DeviceType::class,
        User::class,
        SmartLampData::class,
        SmartPlugData::class,
        ThermostatData::class
    ],
    version = 1,
    exportSchema = false
)
abstract class ServerDatabase: RoomDatabase()  {
    abstract val userDao: UserDao
    abstract val deviceDao: DeviceDao
    abstract val deviceTypeDao: DeviceTypeDao
    abstract val deviceModelDao: DeviceModelDao
}