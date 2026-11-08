package com.sweak.qralarm.alarm.util

import android.media.AudioManager
import com.sweak.qralarm.core.domain.alarm.resolveLockedAlarmVolume
import javax.inject.Inject

/**
 * Keeps the alarm volume where it belongs while an alarm is ringing.
 *
 * Android does not let an app block the volume buttons - the system handles them before the app
 * ever sees them, and there is no permission that changes that. What an app can do is notice that
 * the alarm volume has been lowered and put it back, which is what this does.
 */
class AlarmVolumeGuard @Inject constructor(
    private val audioManager: AudioManager
) {

    /** The volume the alarm stream is at right now. */
    fun currentVolume(): Int = audioManager.getAlarmVolume()

    /**
     * Puts the alarm volume back to [lockedVolume] when it has been lowered below it or muted.
     * Returns true when the volume had to be restored.
     */
    fun restoreVolumeIfLowered(lockedVolume: Int): Boolean {
        val current = currentVolume()
        val isMuted = audioManager.isAlarmStreamMuted()
        val volumeToRestore = resolveLockedAlarmVolume(
            currentVolume = current,
            lockedVolume = lockedVolume
        )

        if (isMuted || volumeToRestore != null) {
            return audioManager.setAlarmVolume(volumeToRestore ?: lockedVolume)
        }

        return false
    }
}
