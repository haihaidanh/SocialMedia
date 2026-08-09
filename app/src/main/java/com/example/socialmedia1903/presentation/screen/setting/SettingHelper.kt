package com.example.socialmedia1903.presentation.screen.setting

import com.example.socialmedia1903.R

data class Language(
    val code: String,
    val name: String,
    val flag: Int
)

object SettingHelper {
    fun listLanguage(): List<Language> {
        return listOf(
            Language("vi", "Tiếng Việt", R.drawable.vietnam),
            Language("en", "English", R.drawable.usa)
        )
    }
}