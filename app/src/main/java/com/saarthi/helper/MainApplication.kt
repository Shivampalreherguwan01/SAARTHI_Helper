package com.saarthi.helper

import android.app.Application
import com.saarthi.helper.security.SecurityManager

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        SecurityManager.initialize(this)
    }

}
