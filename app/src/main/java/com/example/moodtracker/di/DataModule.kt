package com.example.moodtracker.di

import com.example.data.RepositoryMoodImpl
import com.example.data.local.mooddatabase.MoodDatabase
import com.example.domain.RepositoryMood
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideRepositoryMood(repositoryMoodImpl: RepositoryMoodImpl): RepositoryMood {
        return repositoryMoodImpl
    }

}