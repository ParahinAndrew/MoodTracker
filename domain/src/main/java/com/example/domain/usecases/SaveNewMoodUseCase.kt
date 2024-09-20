package com.example.domain.usecases

import com.example.domain.RepositoryMood
import com.example.domain.models.MoodCardDomain
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class SaveNewMoodUseCase @Inject constructor(
    private val moodRepository: RepositoryMood
) {

    fun saveMood(mood: MoodCardDomain) {
        CoroutineScope(Dispatchers.IO).launch {
            moodRepository.saveMood(mood)
        }
    }

}