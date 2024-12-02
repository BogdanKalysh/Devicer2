package com.bkalysh.devicer2

import androidx.room.Room
import com.bkalysh.devicer2.viewmodels.LoginViewModel
import com.bkalysh.devicer2.viewmodels.MainViewModel
import com.bkalysh.devicer2.viewmodels.SignUpViewModel
import com.bkalysh.devicer2.database.DevicerDatabase
import com.bkalysh.devicer2.database.repository.DeviceModelRepository
import com.bkalysh.devicer2.database.repository.DeviceRepository
import com.bkalysh.devicer2.database.repository.DeviceTypeRepository
import com.bkalysh.devicer2.database.repository.DevicerRepositoryFacade
import com.bkalysh.devicer2.mocked.api.MockedServerAPI
import com.bkalysh.devicer2.viewmodels.DeviceInfoViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // Provide API implementation
    single<ServerAPI> { MockedServerAPI(get(), get(), get(), get()) }

    // Providing local devicer database
    single {
        Room.databaseBuilder(androidContext(), DevicerDatabase::class.java, "devicer_database").build()
    }
    // Provide Repositories
    single { DeviceTypeRepository(get<DevicerDatabase>().deviceTypeDao) }
    single { DeviceModelRepository(get<DevicerDatabase>().deviceModelDao) }
    single { DeviceRepository(get<DevicerDatabase>().deviceDao) }
    single {DevicerRepositoryFacade(get(), get(), get())}

    viewModel { LoginViewModel(get()) }
    viewModel { SignUpViewModel(get()) }
    viewModel { MainViewModel(get(), get()) }
    viewModel { DeviceInfoViewModel(get(), get()) }
}