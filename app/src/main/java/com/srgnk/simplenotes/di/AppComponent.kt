package com.srgnk.simplenotes.di

import android.content.Context
import com.srgnk.simplenotes.SimpleNotesApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidSupportInjectionModule::class,
        ActivityBindingModule::class,
        NavigateModule::class,
        DatabaseModule::class]
)
interface AppComponent : AndroidInjector<SimpleNotesApp> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}