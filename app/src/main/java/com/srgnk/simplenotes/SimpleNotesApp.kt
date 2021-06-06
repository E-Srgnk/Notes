package com.srgnk.simplenotes

import android.app.Application
import com.srgnk.simplenotes.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class SimpleNotesApp: DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerAppComponent.factory().create(this)
}