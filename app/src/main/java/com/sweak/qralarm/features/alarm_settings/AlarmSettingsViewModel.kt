package com.sweak.qralarm.features.alarm_settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sweak.qralarm.core.domain.user.UserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlarmSettingsViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository
) : ViewModel() {

    private val _state = MutableStateFlow(AlarmSettingsScreenState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            userDataRepository.isAlarmVolumeLockEnabled.collect { isEnabled ->
                _state.update { currentState ->
                    currentState.copy(isVolumeLockEnabled = isEnabled)
                }
            }
        }
    }

    fun onEvent(event: AlarmSettingsScreenUserEvent) {
        when (event) {
            is AlarmSettingsScreenUserEvent.VolumeLockEnabledChanged -> viewModelScope.launch {
                userDataRepository.setAlarmVolumeLockEnabled(enabled = event.isEnabled)

                _state.update { currentState ->
                    currentState.copy(isVolumeLockEnabled = event.isEnabled)
                }
            }

            else -> { /* no-op */
            }
        }
    }
}
