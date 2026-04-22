package com.example.socialmedia1903.presentation.screen.setting


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.example.socialmedia1903.data.repository.SettingLanguageRepositoryImpl
import com.example.socialmedia1903.data.repository.SettingThemeRepositoryImpl
import com.example.socialmedia1903.domain.enums.ThemeMode
import com.example.socialmedia1903.domain.repository.SettingLanguageRepository
import com.example.socialmedia1903.domain.repository.SettingThemeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: SettingLanguageRepository,
    private val themeRepository: SettingThemeRepository
) : ViewModel() {

    val language: StateFlow<String> = repository
        .getLanguage()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = "en"
        )

    val theme = themeRepository.getAppTheme()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ThemeMode.SYSTEM
        )

    fun changeLanguage(lang: String) {
        viewModelScope.launch {
            repository.saveLanguage(lang)
            setAppLanguage(lang)
        }
    }

    fun changeTheme(themeMode: ThemeMode) {
        viewModelScope.launch {
            themeRepository.saveTheme(themeMode)
            //setAppTheme(themeMode)
        }
    }

    private fun setAppLanguage(language: String) {
        val localeList = LocaleListCompat.forLanguageTags(language)
        AppCompatDelegate.setApplicationLocales(localeList)
    }
}