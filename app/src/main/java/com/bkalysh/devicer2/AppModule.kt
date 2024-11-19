package com.bkalysh.devicer2

import androidx.room.Room
import com.bkalysh.devicer2.mocked.backend.ServerAPI
import com.bkalysh.devicer2.mocked.backend.db.ServerDatabase
import com.bkalysh.devicer2.mocked.backend.db.repository.DeviceModelRepository
import com.bkalysh.devicer2.mocked.backend.db.repository.DeviceRepository
import com.bkalysh.devicer2.mocked.backend.db.repository.DeviceTypeRepository
import com.bkalysh.devicer2.mocked.backend.db.repository.UserRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    // Provide ServerDatabase instance
    single { Room.databaseBuilder(androidContext(), ServerDatabase::class.java, "server_database").build() }

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

    single { ServerAPI(get(), get(), get(), get())}
}