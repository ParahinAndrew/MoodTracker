package com.example.data.local.mooddatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.data.local.mooddatabase.models.MoodCardEntity

@Dao
interface MoodDao {

    @Query("SELECT * FROM mood_card")
    fun getAll(): List<MoodCardEntity>

    @Insert
    fun insert(moodCard: MoodCardEntity)

    @Query("DELETE FROM mood_card WHERE mood = :mood")
    fun delete(mood: String)

    @Query("DELETE FROM mood_card")
    fun deleteAll()

}