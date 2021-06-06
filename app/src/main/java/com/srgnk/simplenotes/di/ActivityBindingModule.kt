package com.srgnk.simplenotes.di

import com.srgnk.simplenotes.ui.activity.AppActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = [ScreenBindingModule::class])
    abstract fun appActivityInjector(): AppActivity
}