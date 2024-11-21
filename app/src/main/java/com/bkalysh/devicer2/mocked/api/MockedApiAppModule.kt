package com.bkalysh.devicer2.mocked.api

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.bkalysh.devicer2.mocked.api.db.ServerDatabase
import com.bkalysh.devicer2.mocked.api.db.repository.DeviceModelRepository
import com.bkalysh.devicer2.mocked.api.db.repository.DeviceRepository
import com.bkalysh.devicer2.mocked.api.db.repository.DeviceTypeRepository
import com.bkalysh.devicer2.mocked.api.db.repository.UserRepository
import com.bkalysh.devicer2.mocked.api.utils.DBPrepopulateFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val mockedApiAppModule = module {
    // Provide ServerDatabase instance
    single {
        Room.databaseBuilder(androidContext(), ServerDatabase::class.java, "server_database")
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    val scope = CoroutineScope(Dispatchers.IO)
                    scope.launch {
                        get<ServerDatabase>().deviceTypeDao.insertAll(DBPrepopulateFactory.getDeviceTypes())
                        get<ServerDatabase>().deviceModelDao.insertAll(DBPrepopulateFactory.getDeviceModels())
                    }
                }
            })
            .build()
    }

    // Provide DAOs from the ServerDatabase instance
    single { get<ServerDatabase>().userDao }
    single { get<ServerDatabase>().deviceDao }
    single { get<ServerDatabase>().deviceTypeDao }
    single { get<ServerDatabase>().deviceModelDao }

    // Provide Repositories
    single { DeviceRepository(get()) }
    single { DeviceModelRepository(get()) }
    single { DeviceTypeRepository(get()) }
    single { UserRepository(get()) }
}