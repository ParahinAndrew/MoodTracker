package com.example.domain.models

import androidx.annotation.DrawableRes
import com.example.domain.R
import java.time.LocalDateTime

data class MoodCardDomain(
    val title: String,
    val mood: String,
    val time: LocalDateTime,
    @DrawableRes val imageResId: Int = R.drawable.alien
)
