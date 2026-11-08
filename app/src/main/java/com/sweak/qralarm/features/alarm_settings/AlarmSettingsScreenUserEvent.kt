package com.sweak.qralarm.features.alarm_settings

sealed class AlarmSettingsScreenUserEvent {
    data object OnBackClicked : AlarmSettingsScreenUserEvent()
    data class VolumeLockEnabledChanged(val isEnabled: Boolean) : AlarmSettingsScreenUserEvent()
}
