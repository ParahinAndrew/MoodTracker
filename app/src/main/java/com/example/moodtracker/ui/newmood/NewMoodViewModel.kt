package com.example.moodtracker.ui.newmood

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.models.MoodCardDomain
import com.example.domain.usecases.SaveNewMoodUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewMoodViewModel @Inject constructor(
    private val saveNewMoodUseCase: SaveNewMoodUseCase
): ViewModel() {

    val mood: MutableLiveData<MoodCardDomain> = MutableLiveData()

    fun saveMood(mood: MoodCardDomain) {
        saveNewMoodUseCase.saveMood(mood)
    }

}