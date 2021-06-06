package com.srgnk.simplenotes.di

import com.srgnk.simplenotes.ui.fragment.MainScreen
import com.srgnk.simplenotes.ui.fragment.NoteScreen
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ScreenBindingModule {

    @ContributesAndroidInjector
    abstract fun mainScreenInjector(): MainScreen

    @ContributesAndroidInjector
    abstract fun noteScreenInjector(): NoteScreen
}