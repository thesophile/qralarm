package com.sweak.qralarm.core.domain.alarm

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import org.junit.Test

class AlarmVolumeTest {

    @Test
    fun `given a custom volume percentage, when calculating its volume index, then it is a part of the stream's range`() {
        // Given
        val volumePercentage = 50

        // When
        val result = calculateAlarmVolumeIndex(
            volumePercentage = volumePercentage,
            minVolume = 1,
            maxVolume = 15
        )

        // Then
        assertThat(result).isEqualTo(8)
    }

    @Test
    fun `given the lowest possible volume percentage, when calculating its volume index, then the stream is not muted`() {
        // Given
        val volumePercentage = 0

        // When
        val result = calculateAlarmVolumeIndex(
            volumePercentage = volumePercentage,
            minVolume = 1,
            maxVolume = 15
        )

        // Then
        assertThat(result).isEqualTo(2)
    }

    @Test
    fun `given a stream without a lower bound, when calculating the lowest volume index, then the stream is not muted`() {
        // Given
        val volumePercentage = 0

        // When
        val result = calculateAlarmVolumeIndex(
            volumePercentage = volumePercentage,
            minVolume = 0,
            maxVolume = 15
        )

        // Then
        assertThat(result).isEqualTo(1)
    }

    @Test
    fun `given the highest possible volume percentage, when calculating its volume index, then it is the maximum volume`() {
        // Given
        val volumePercentage = 100

        // When
        val result = calculateAlarmVolumeIndex(
            volumePercentage = volumePercentage,
            minVolume = 1,
            maxVolume = 15
        )

        // Then
        assertThat(result).isEqualTo(15)
    }

    @Test
    fun `given a volume lowered below the locked one, when resolving it, then the locked volume is restored`() {
        // Given
        val currentVolume = 2
        val lockedVolume = 7

        // When
        val result = resolveLockedAlarmVolume(
            currentVolume = currentVolume,
            lockedVolume = lockedVolume
        )

        // Then
        assertThat(result).isEqualTo(lockedVolume)
    }

    @Test
    fun `given a muted volume, when resolving it, then the locked volume is restored`() {
        // Given
        val currentVolume = 0
        val lockedVolume = 7

        // When
        val result = resolveLockedAlarmVolume(
            currentVolume = currentVolume,
            lockedVolume = lockedVolume
        )

        // Then
        assertThat(result).isEqualTo(lockedVolume)
    }

    @Test
    fun `given a volume at the locked one, when resolving it, then nothing has to be restored`() {
        // Given
        val currentVolume = 7
        val lockedVolume = 7

        // When
        val result = resolveLockedAlarmVolume(
            currentVolume = currentVolume,
            lockedVolume = lockedVolume
        )

        // Then
        assertThat(result).isNull()
    }

    @Test
    fun `given a volume raised above the locked one, when resolving it, then nothing has to be restored`() {
        // Given
        val currentVolume = 12
        val lockedVolume = 7

        // When
        val result = resolveLockedAlarmVolume(
            currentVolume = currentVolume,
            lockedVolume = lockedVolume
        )

        // Then
        assertThat(result).isNull()
    }
}
