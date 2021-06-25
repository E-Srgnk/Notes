package com.srgnk.simplenotes.di

import com.srgnk.simplenotes.mvp.model.DatabaseRepository
import com.srgnk.simplenotes.mvp.model.NoteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {
    @Binds
    abstract fun provideRepository(repo: NoteRepository): DatabaseRepository
}