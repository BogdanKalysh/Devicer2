package com.bkalysh.devicer2

interface ServerAPI {
    suspend fun loginUser(email: String, password: String): String
    suspend fun registerUser(email: String, name: String, password: String): String
    suspend fun getUserName(jwtToken: String): String
    suspend fun getDeviceTypes(): String
    suspend fun getDeviceModels(): String
    suspend fun addDevice(jwtToken: String, deviceJson: String): Result<String>
    suspend fun getAllDevices(jwtToken: String): String
    suspend fun updateDevicePowerState(jwtToken: String, deviceJson: String): Result<String>
    suspend fun deleteDevice(jwtToken: String, deviceJson: String): Result<String>
}