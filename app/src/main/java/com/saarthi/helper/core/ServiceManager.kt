package com.saarthi.helper.core

import android.content.Context
import com.saarthi.helper.router.MessageRouter

object ServiceManager {

    private lateinit var router: MessageRouter

    private lateinit var bootstrap: Bootstrap

    fun initialize(
        appContext: Context
    ) {

        val context =
            appContext.applicationContext

        router =
            MessageRouter(context)

        bootstrap =
            Bootstrap(
                context,
                router
            )

        bootstrap.start()
    }

    fun shutdown() {

        if (::bootstrap.isInitialized) {
            bootstrap.stop()
        }
    }

    fun getRouter(): MessageRouter {
        return router
    }
}
