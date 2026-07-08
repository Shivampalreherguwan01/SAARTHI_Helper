package com.saarthi.helper.router

import com.saarthi.helper.model.Message
import com.saarthi.helper.plugins.PluginManager

class MessageRouter {

    private lateinit var plugins: PluginManager

    fun attach(

        pluginManager: PluginManager

    ) {

        plugins = pluginManager

    }

    fun dispatch(

        message: Message

    ) {

        if (::plugins.isInitialized) {

            plugins.dispatch(message)

        }

    }

}
