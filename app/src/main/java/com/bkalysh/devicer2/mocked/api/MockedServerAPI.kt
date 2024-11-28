package com.bkalysh.devicer2.mocked.api

import com.bkalysh.devicer2.ServerAPI
import com.bkalysh.devicer2.mocked.api.db.models.Device
import com.bkalysh.devicer2.mocked.api.db.models.User
import com.bkalysh.devicer2.mocked.api.db.models.unique.fields.SmartLampData
import com.bkalysh.devicer2.mocked.api.db.models.unique.fields.SmartPlugData
import com.bkalysh.devicer2.mocked.api.db.models.unique.fields.ThermostatData
import com.bkalysh.devicer2.mocked.api.db.repository.DeviceModelRepository
import com.bkalysh.devicer2.mocked.api.db.repository.DeviceRepository
import com.bkalysh.devicer2.mocked.api.db.repository.DeviceTypeRepository
import com.bkalysh.devicer2.mocked.api.db.repository.UserRepository
import com.bkalysh.devicer2.mocked.api.utils.JWT.Companion.createJwtToken
import com.bkalysh.devicer2.mocked.api.utils.JWT.Companion.decodeJwtToken
import com.google.gson.Gson

class MockedServerAPI(
    private val deviceRepository: DeviceRepository,
    private val deviceModelRepository: DeviceModelRepository,
    private val deviceTypeRepository: DeviceTypeRepository,
    private val userRepository: UserRepository
): ServerAPI {
    private val gson = Gson()

    override suspend fun loginUser(email: String, password: String): String {
        val user: User? = userRepository.getUserByEmail(email)

        if (user != null && user.password == password) {
            return createJwtToken(user.email, user.name)
        } else {
            throw IllegalArgumentException("Invalid credentials")
        }
    }

    override suspend fun registerUser(email: String, name: String, password: String): String {
        val newUser = User(email = email, name = name, password = password)

        try {
            userRepository.insertUser(newUser)
        } catch (e: Exception) {
            throw IllegalArgumentException("User with this email already exists.")
        }

        return createJwtToken(newUser.email, newUser.name)
    }

    override suspend fun getUserName(jwtToken: String): String {
        try {
            val tokenData = decodeJwtToken(jwtToken)
            return tokenData["user_name"].toString()
        } catch (e: Exception) {
            throw IllegalArgumentException("Invalid token: ${e.message}")
        }
    }

    override suspend fun getDeviceTypes(): String {
        return gson.toJson(deviceTypeRepository.getAllDeviceTypes())
    }

    override suspend fun getDeviceModels(): String {
        return gson.toJson(deviceModelRepository.getAllDeviceModels())
    }

    override suspend fun addDevice(jwtToken: String, deviceJson: String): Result<String> {
        val device = gson.fromJson(deviceJson, Device::class.java)
        val tokenData = decodeJwtToken(jwtToken)
        val userId = userRepository.getUserByEmail(tokenData["email"].toString())?.id ?: 0

        val newDevice = device.copy(ownerId = userId)

        try {
            deviceRepository.insertDevice(newDevice)
        } catch (e: Exception) {
            return Result.failure(e)
        }

        val resultDevice = deviceRepository.getDeviceBySerial(newDevice.serialNumber)
        resultDevice?.let { insertDeviceSpecificData(it) }
        return Result.success(gson.toJson(resultDevice))
    }

    private suspend fun insertDeviceSpecificData(device: Device) {
        val deviceModel = deviceModelRepository.getDeviceModelById(device.deviceModelId)
        val deviceType = deviceModel?.let { model ->
            deviceTypeRepository.getDeviceTypeById(model.deviceTypeId)
        }

        when (deviceType?.name) {
            "Smart lamp" -> deviceRepository.upsertLampData(SmartLampData(device.id, 0))
            "Smart plug" -> deviceRepository.upsertPlugData(SmartPlugData(device.id, 45))
            "Thermostat" -> deviceRepository.upsertThermostatData(ThermostatData(device.id, 25, 25))
        }
    }

    override suspend fun getAllDevices(jwtToken: String): String {
        val tokenData = decodeJwtToken(jwtToken)
        val ownerId = userRepository.getUserByEmail(tokenData["email"].toString())?.id ?: 0

        return gson.toJson(deviceRepository.getDevicesByOwnerId(ownerId))
    }
}