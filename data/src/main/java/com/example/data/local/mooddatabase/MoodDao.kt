package com.example.data.local.mooddatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.data.local.mooddatabase.models.MoodCardEntity
import java.time.LocalDateTime

@Dao
interface MoodDao {

    @Query("SELECT * FROM mood_card")
    fun getAll(): List<MoodCardEntity>

    @Insert
    fun insert(moodCard: MoodCardEntity)

    @Query("DELETE FROM mood_card WHERE time = :time")
    fun delete(time: LocalDateTime)

    @Query("DELETE FROM mood_card")
    fun deleteAll()

}