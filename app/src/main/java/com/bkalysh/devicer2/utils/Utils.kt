package com.bkalysh.devicer2.utils

import com.bkalysh.devicer2.R
import kotlin.random.Random

object Utils {
    fun generateRandomSerial(length: Int = 12): String {
        val charPool = ('0'..'9') + ('A'..'Z') // Numbers and uppercase letters
        return (1..length)
            .map { Random.nextInt(charPool.size) }
            .map(charPool::get)
            .joinToString("")
    }

    fun generateRandomMacAddress(): String {
        val hexChars = "0123456789ABCDEF"
        return (1..6).joinToString(":") {
            (1..2).map { hexChars.random() }.joinToString("")
        }
    }

    fun generateRandomFirmwareVersion(): String {
        val major = Random.nextInt(1, 4)
        val minor = if (major == 3) 0 else Random.nextInt(0, 10)
        val patch = if (major == 3) 0 else Random.nextInt(0, 10)

        return "$major.$minor.$patch"
    }

    fun mapModelToImageResource(modelId: Int): Int {
        val images = listOf(
            R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background
        )

        return images.elementAtOrNull(modelId) ?: R.drawable.ic_launcher_foreground
    }
}