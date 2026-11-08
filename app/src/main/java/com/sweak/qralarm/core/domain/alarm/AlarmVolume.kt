package com.sweak.qralarm.core.domain.alarm

/**
 * The volume an alarm with a custom volume percentage rings at, as an index of the alarm stream.
 *
 * The lowest index of the stream is never returned - an alarm that rings at the lowest possible
 * volume is indistinguishable from a muted one.
 */
fun calculateAlarmVolumeIndex(
    volumePercentage: Int,
    minVolume: Int,
    maxVolume: Int
): Int {
    val volumeRange = maxVolume - minVolume

    return (minVolume + (volumeRange * (volumePercentage / 100.0))).toInt()
        .coerceAtLeast(minVolume + 1)
}

/**
 * The volume the alarm stream has to be put back to when it has been lowered below the level the
 * alarm rings at. Null means the volume is where it belongs and nothing has to be done.
 */
fun resolveLockedAlarmVolume(
    currentVolume: Int,
    lockedVolume: Int
): Int? = if (currentVolume < lockedVolume) lockedVolume else null
