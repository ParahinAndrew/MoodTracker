package com.example.moodtracker.ui.home.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.MoodCardDomain
import com.example.domain.usecases.DeleteMoodItemUseCase
import com.example.domain.usecases.GetMoodCardUseCase
import com.example.moodtracker.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class MoodListViewModel @Inject constructor(
    private val getMoodCardUseCase: GetMoodCardUseCase,
    private val deleteMoodItemUseCase: DeleteMoodItemUseCase
) : ViewModel() {

    private var _moods: MutableLiveData<List<MoodCardDomain>> = MutableLiveData()
    val moods = _moods

    init {
        viewModelScope.launch {
            val moods = withContext(Dispatchers.IO) {
                getMoodCardUseCase.getAllMoods()
            }
            _moods.value = moods
        }
    }

    fun deleteAndUpdateMood(mood: MoodCardDomain) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                deleteMoodItemUseCase.deleteMood(mood)
            }
            val moods = withContext(Dispatchers.IO) {
                getMoodCardUseCase.getAllMoods()
            }
            _moods.value = moods
        }
    }


}