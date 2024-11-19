package com.bkalysh.devicer2.mocked.backend.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import androidx.room.ColumnInfo
import androidx.room.Index

@Entity(
    tableName = "device",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["owner_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = DeviceModel::class,
            parentColumns = ["id"],
            childColumns = ["device_model_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["serial_number"], unique = true), Index(value = ["mac_address"], unique = true), Index(value = ["owner_id"]), Index(value = ["device_model_id"])]
)
data class Device(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "serial_number") val serialNumber: String,
    @ColumnInfo(name = "mac_address") val macAddress: String,
    @ColumnInfo(name = "firmware_version") val firmwareVersion: String,
    @ColumnInfo(name = "owner_id") val ownerId: Long,  // Foreign key to User
    @ColumnInfo(name = "device_model_id") val deviceModelId: Long,  // Foreign key to DeviceModel
    @ColumnInfo(name = "is_powered") val isPowered: Boolean
)