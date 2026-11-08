package com.sweak.qralarm.alarm.util

import android.media.AudioManager
import android.os.Build

/**
 * Sets the volume of the alarm stream. Returns false when the system refused to change it, which
 * happens without the MODIFY_AUDIO_SETTINGS permission and on devices with a fixed volume.
 */
fun AudioManager.setAlarmVolume(index: Int): Boolean {
    return try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && isStreamMute(AudioManager.STREAM_ALARM)) {
            adjustStreamVolume(AudioManager.STREAM_ALARM, AudioManager.ADJUST_UNMUTE, 0)
        }
        setStreamVolume(
            AudioManager.STREAM_ALARM,
            index,
            0
        )
        true
    } catch (_: SecurityException) {
        false
    }
}

fun AudioManager.isAlarmStreamMuted(): Boolean =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        isStreamMute(AudioManager.STREAM_ALARM)
    } else {
        getAlarmVolume() == 0
    }

fun AudioManager.getAlarmVolume(): Int = getStreamVolume(AudioManager.STREAM_ALARM)

fun AudioManager.getMaxAlarmVolume(): Int = getStreamMaxVolume(AudioManager.STREAM_ALARM)

fun AudioManager.getMinAlarmVolume(): Int =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        getStreamMinVolume(AudioManager.STREAM_ALARM)
    } else {
        0
    }
