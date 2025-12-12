package com.example.animeapplication.core.security

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.provider.Settings
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

enum class DeviceSecurityStatus {
    SECURE,
    ROOTED,
    EMULATOR
}

@Singleton
class SecurityManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    fun checkDeviceSecurity(): DeviceSecurityStatus {
        return when {
            isDeviceRooted() -> DeviceSecurityStatus.ROOTED
            isEmulator() -> DeviceSecurityStatus.EMULATOR
            else -> DeviceSecurityStatus.SECURE
        }
    }

    fun isSecure(): Boolean = checkDeviceSecurity() == DeviceSecurityStatus.SECURE

    private fun isDeviceRooted(): Boolean {
        val rootPaths = arrayOf(
            "/system/app/Superuser.apk",
            "/system/xbin/su",
            "/system/bin/su",
            "/sbin/su",
            "/data/local/xbin/su",
            "/data/local/bin/su",
            "/data/local/su"
        )
        return rootPaths.any { File(it).exists() } || canExecuteSu()
    }

    private fun canExecuteSu(): Boolean {
        return try {
            Runtime.getRuntime().exec("su")
            true
        } catch (e: Exception) {
            false
        }
    }

    private fun isEmulator(): Boolean {
        return Build.FINGERPRINT.startsWith("generic") ||
                Build.FINGERPRINT.startsWith("unknown") ||
                Build.MODEL.contains("google_sdk") ||
                Build.MODEL.contains("Emulator") ||
                Build.MODEL.contains("Android SDK built for x86") ||
                Build.MANUFACTURER.contains("Genymotion") ||
                Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic") ||
                Build.PRODUCT == "sdk" ||
                Build.PRODUCT == "google_sdk" ||
                Build.PRODUCT == "sdk_x86" ||
                Build.PRODUCT == "vbox86p" ||
                Build.HARDWARE.contains("goldfish") ||
                Build.HARDWARE.contains("ranchu")
    }

    @SuppressLint("HardwareIds")
    fun isDeveloperModeEnabled(): Boolean {
        return Settings.Global.getInt(
            context.contentResolver,
            Settings.Global.DEVELOPMENT_SETTINGS_ENABLED,
            0
        ) != 0
    }
}
