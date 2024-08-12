package com.prafull.notesapp

import android.app.Application
import com.prafull.notesapp.auth.authModule
import com.prafull.notesapp.main.di.mainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class NotesApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(mainModule, authModule)
            androidContext(this@NotesApp)
        }
    }
}