package com.example.data.local.mooddatabase.models

import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.R
import com.example.domain.models.MoodCardDomain
import java.time.LocalDateTime

@Entity(tableName = "mood_card")
data class MoodCardEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val mood: String,
    val time: LocalDateTime,
    @DrawableRes val imageResId: Int = R.drawable.alien,
)

fun MoodCardEntity.toMoodCard() = MoodCardDomain(
    title = title,
    mood = mood,
    time = time,
    imageResId = imageResId
)

fun MoodCardDomain.toMoodCardEntity() = MoodCardEntity(
    title = title,
    mood = mood,
    time = time,
    imageResId = imageResId
)