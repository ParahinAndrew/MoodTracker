package com.example.data.local.mooddatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data.local.mooddatabase.models.MoodCardEntity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Database(entities = [MoodCardEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class MoodDatabase: RoomDatabase() {
    abstract fun moodDao(): MoodDao
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesMoodDatabase(@ApplicationContext context: Context): MoodDatabase {
        return Room.databaseBuilder(
            context,
            MoodDatabase::class.java,
            "mood.db"
        ).fallbackToDestructiveMigration().build()  // fallbackToDestructiveMigration() - удаляет данные и пересоздает БД при обновлении таблицы
    }

    @Provides
    @Singleton
    fun providesMoodDao(dataBaseMood: MoodDatabase): MoodDao {
        return dataBaseMood.moodDao()
    }

}
