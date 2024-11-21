package com.bkalysh.devicer2.mocked.api.db.models.unique.fields
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import com.bkalysh.devicer2.mocked.api.db.models.Device

@Entity(
    tableName = "thermostat_data",
    foreignKeys = [
        ForeignKey(
            entity = Device::class,
            parentColumns = ["id"],
            childColumns = ["device_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ThermostatData(
    @PrimaryKey @ColumnInfo(name = "device_id") val deviceId: Long, // Primary key and foreign key
    @ColumnInfo(name = "room_temperature") val roomTemperature: Int,
    @ColumnInfo(name = "goal_temperature")  val goalTemperature: Int
)