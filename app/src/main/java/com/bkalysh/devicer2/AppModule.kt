package com.bkalysh.devicer2

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.bkalysh.devicer2.activities.viewmodels.LoginViewModel
import com.bkalysh.devicer2.activities.viewmodels.MainViewModel
import com.bkalysh.devicer2.activities.viewmodels.SignUpViewModel
import com.bkalysh.devicer2.mocked.backend.MockedServerAPI
import com.bkalysh.devicer2.mocked.backend.db.ServerDatabase
import com.bkalysh.devicer2.mocked.backend.db.repository.DeviceModelRepository
import com.bkalysh.devicer2.mocked.backend.db.repository.DeviceRepository
import com.bkalysh.devicer2.mocked.backend.db.repository.DeviceTypeRepository
import com.bkalysh.devicer2.mocked.backend.db.repository.UserRepository
import com.bkalysh.devicer2.mocked.backend.utils.DBPrepopulateFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
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

    single<ServerAPI> { MockedServerAPI(get(), get(), get(), get())}

    viewModel { LoginViewModel(get()) }
    viewModel { SignUpViewModel(get()) }
    viewModel { MainViewModel(get()) }
}