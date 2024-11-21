package com.bkalysh.devicer2.mocked.api.utils

import com.bkalysh.devicer2.mocked.api.db.models.DeviceModel
import com.bkalysh.devicer2.mocked.api.db.models.DeviceType

object DBPrepopulateFactory {
    fun getDeviceTypes(): List<DeviceType> =
        listOf(
            DeviceType(name = "Smart lamp"),
            DeviceType(name = "Smart plug"),
            DeviceType(name = "Thermostat")
        )

    fun getDeviceModels(): List<DeviceModel> =
        listOf(
            DeviceModel(deviceTypeId = 1, name = "LuminaBright Pro"),
            DeviceModel(deviceTypeId = 1, name = "GlowSphere X1"),
            DeviceModel(deviceTypeId = 1, name = "IntelliLight Aura"),
            DeviceModel(deviceTypeId = 2, name = "PowerLink Mini"),
            DeviceModel(deviceTypeId = 2, name = "VoltMaster Plus"),
            DeviceModel(deviceTypeId = 2, name = "SmartSwitch Edge"),
            DeviceModel(deviceTypeId = 3, name = "ClimateSync 360"),
            DeviceModel(deviceTypeId = 3, name = "ThermoWise Elite"),
            DeviceModel(deviceTypeId = 3, name = "EcoTemp Vision"),
        )
}