package com.bkalysh.devicer2.mocked.backend.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "device_type")
data class DeviceType(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String
)