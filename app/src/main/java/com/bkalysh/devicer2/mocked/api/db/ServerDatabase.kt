package com.bkalysh.devicer2.mocked.api.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bkalysh.devicer2.mocked.api.db.dao.DeviceDao
import com.bkalysh.devicer2.mocked.api.db.dao.DeviceModelDao
import com.bkalysh.devicer2.mocked.api.db.dao.DeviceTypeDao
import com.bkalysh.devicer2.mocked.api.db.dao.UserDao
import com.bkalysh.devicer2.mocked.api.db.models.Device
import com.bkalysh.devicer2.mocked.api.db.models.DeviceModel
import com.bkalysh.devicer2.mocked.api.db.models.DeviceType
import com.bkalysh.devicer2.mocked.api.db.models.User
import com.bkalysh.devicer2.mocked.api.db.models.unique.fields.SmartLampData
import com.bkalysh.devicer2.mocked.api.db.models.unique.fields.SmartPlugData
import com.bkalysh.devicer2.mocked.api.db.models.unique.fields.ThermostatData

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