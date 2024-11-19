package com.bkalysh.devicer2.mocked.backend

import com.bkalysh.devicer2.mocked.backend.db.model.Device
import com.bkalysh.devicer2.mocked.backend.db.model.DeviceModel
import com.bkalysh.devicer2.mocked.backend.db.model.DeviceType
import com.bkalysh.devicer2.mocked.backend.db.model.User
import com.bkalysh.devicer2.mocked.backend.db.repository.DeviceModelRepository
import com.bkalysh.devicer2.mocked.backend.db.repository.DeviceRepository
import com.bkalysh.devicer2.mocked.backend.db.repository.DeviceTypeRepository
import com.bkalysh.devicer2.mocked.backend.db.repository.UserRepository

// Facade for the whole mocked backend API
class ServerAPI(
    private val deviceRepository: DeviceRepository,
    private val deviceModelRepository: DeviceModelRepository,
    private val deviceTypeRepository: DeviceTypeRepository,
    private val userRepository: UserRepository
) {
    suspend fun upsertUser(user: User) = userRepository.upsertUser(user)
    suspend fun upsertDeviceType(deviceType: DeviceType) = deviceTypeRepository.upsertDeviceType(deviceType)
    suspend fun upsertDeviceModel(deviceModel: DeviceModel) = deviceModelRepository.upsertDeviceModel(deviceModel)
    suspend fun upsertDevice(device: Device) = deviceRepository.upsertDevice(device)

    suspend fun deleteUser(user: User) = userRepository.deleteUser(user)
    suspend fun deleteDeviceType(deviceType: DeviceType) = deviceTypeRepository.deleteDeviceType(deviceType)
    suspend fun deleteDeviceModel(deviceModel: DeviceModel) = deviceModelRepository.deleteDeviceModel(deviceModel)
    suspend fun deleteDevice(device: Device) = deviceRepository.deleteDevice(device)



    // Example method to get a user by ID
    suspend fun getUserById(id: Long) = userRepository.getUserById(id)

    // Example method to get a user by email
    suspend fun getUserByEmail(email: String) = userRepository.getUserByEmail(email)

    // Example method to get devices by owner ID
    suspend fun getDevicesByOwnerId(ownerId: Long) = deviceRepository.getDevicesByOwnerId(ownerId)

    // Example method to get all device types
    fun getAllDeviceTypes() = deviceTypeRepository.getAllDeviceTypes()

    // Example method to get a device type by ID
    suspend fun getDeviceTypeById(id: Long) = deviceTypeRepository.getDeviceTypeById(id)

    // Example method to get a device model by ID
    suspend fun getDeviceModelById(id: Long) = deviceModelRepository.getDeviceModelById(id)

    // Add more methods to expose other operations as needed
}