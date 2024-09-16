package com.example.domain

import com.example.domain.models.MoodCardDomain


interface RepositoryMood {

    suspend fun getAllMoods(): List<MoodCardDomain>

    suspend fun saveMood(mood: MoodCardDomain)

    suspend fun deleteMood(mood: MoodCardDomain)

    suspend fun deleteAllMoods()

}