package com.example.moodtracker.ui.home.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.MoodCardDomain
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
    private val getMoodCardUseCase: GetMoodCardUseCase
) : ViewModel() {

    private var _moods: MutableLiveData<List<MoodCardDomain>> = MutableLiveData()
    val moods = _moods

    init {
        viewModelScope.launch {
            // Выполняем запрос на фоновом потоке (IO)
            var moods = withContext(Dispatchers.IO) {
                getMoodCardUseCase.getAllMoods()
            }
            // Возвращаем результат на главный поток (Main)
            if (moods.isEmpty()) {
                moods = listOf(MoodCardDomain(title = "Empty", mood = "Empty", time = LocalDateTime.now()))
            }
            _moods.value = moods
        }
    }

    /*private suspend fun getAllMoods(){
        _moods.value = getMoodCardUseCase.getAllMoods()
        if (_moods.value == null) {
            _moods.value = emptyList()
        }
    }*/

}