package com.bkalysh.devicer2.mocked.backend.db.model.unique.state
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import com.bkalysh.devicer2.mocked.backend.db.model.Device

@Entity(
    tableName = "smart_plug_data",
    foreignKeys = [
        ForeignKey(
            entity = Device::class,
            parentColumns = ["id"],
            childColumns = ["device_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class SmartPlugData(
    @PrimaryKey @ColumnInfo(name = "device_id") val deviceId: Long, // Primary key and foreign key
    val wattage: Int // Energy consumpsion in Watts
)