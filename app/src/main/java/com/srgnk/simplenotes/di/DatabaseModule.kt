package com.srgnk.simplenotes.di

import android.content.Context
import androidx.room.Room
import com.srgnk.simplenotes.mvp.model.NoteDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(context: Context): NoteDatabase =
        Room.databaseBuilder(context, NoteDatabase::class.java, "note").build()
}