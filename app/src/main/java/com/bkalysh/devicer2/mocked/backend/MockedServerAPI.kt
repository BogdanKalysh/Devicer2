package com.bkalysh.devicer2.mocked.backend

import com.bkalysh.devicer2.ServerAPI
import com.bkalysh.devicer2.mocked.backend.db.models.User
import com.bkalysh.devicer2.mocked.backend.db.repository.DeviceModelRepository
import com.bkalysh.devicer2.mocked.backend.db.repository.DeviceRepository
import com.bkalysh.devicer2.mocked.backend.db.repository.DeviceTypeRepository
import com.bkalysh.devicer2.mocked.backend.db.repository.UserRepository
import com.bkalysh.devicer2.mocked.backend.utils.JWT.Companion.createJwtToken
import com.bkalysh.devicer2.mocked.backend.utils.JWT.Companion.decodeJwtToken
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
            return createJwtToken(user.id.toString(), user.name)
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

        return createJwtToken(newUser.id.toString(), newUser.name)
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
}