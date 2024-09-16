package com.example.domain.usecases

import com.example.domain.RepositoryMood
import com.example.domain.models.MoodCardDomain
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class GetMoodCardUseCase @Inject constructor(private val moodRepository: RepositoryMood) {

    suspend fun getAllMoods() = moodRepository.getAllMoods()

}