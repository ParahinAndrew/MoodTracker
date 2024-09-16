package com.example.data

import com.example.data.local.mooddatabase.MoodDao
import com.example.data.local.mooddatabase.MoodDatabase
import com.example.data.local.mooddatabase.models.MoodCardEntity
import com.example.data.local.mooddatabase.models.toMoodCard
import com.example.data.local.mooddatabase.models.toMoodCardEntity
import com.example.domain.RepositoryMood
import com.example.domain.models.MoodCardDomain
import javax.inject.Inject


class RepositoryMoodImpl @Inject constructor(
    private val moodDatabase: MoodDao
) : RepositoryMood {

    override suspend fun getAllMoods(): List<MoodCardDomain> {
        return moodDatabase.getAll().map { it.toMoodCard() }
    }

    override suspend fun saveMood(mood: MoodCardDomain) {
        return moodDatabase.insert(mood.toMoodCardEntity())
    }

    override suspend fun deleteMood(mood: MoodCardDomain) {
        return moodDatabase.delete(mood.mood)
    }

    override suspend fun deleteAllMoods() {
        return moodDatabase.deleteAll()
    }

}
