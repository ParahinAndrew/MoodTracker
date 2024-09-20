package com.example.domain.usecases

import com.example.domain.RepositoryMood
import com.example.domain.models.MoodCardDomain
import javax.inject.Inject

class DeleteMoodItemUseCase @Inject constructor(
    private val repositoryMood: RepositoryMood
) {
    suspend fun deleteMood(mood: MoodCardDomain) {
        repositoryMood.deleteMood(mood)
    }
}