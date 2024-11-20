package com.bkalysh.devicer2.mocked.backend.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "device_model",
    foreignKeys = [
        ForeignKey(
            entity = DeviceType::class,
            parentColumns = ["id"],
            childColumns = ["device_type_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["device_type_id"])]
)
data class DeviceModel(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "device_type_id") val deviceTypeId: Long,  // Foreign key to DeviceType
    val name: String
)