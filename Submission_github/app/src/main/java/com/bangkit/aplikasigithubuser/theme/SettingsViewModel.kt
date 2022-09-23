package com.bangkit.aplikasigithubuser.theme

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class SettingsViewModel(private val pref: PrefHelper) : ViewModel() {
    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }
}

