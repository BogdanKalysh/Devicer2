package com.bkalysh.devicer2.database.repository

class DevicerRepositoryFacade (
    val deviceTypeRepository: DeviceTypeRepository,
    val deviceModelRepository: DeviceModelRepository,
    val deviceRepository: DeviceRepository
)