package com.saarthi.helper.plugins

import com.saarthi.helper.model.Message

class PluginManager {

    private val plugins = mutableListOf<Plugin>()

    fun register(plugin: Plugin) {

        plugins.add(plugin)

    }

    fun startAll() {

        plugins.forEach {

            it.start()

        }

    }

    fun stopAll() {

        plugins.forEach {

            it.stop()

        }

    }

    fun dispatch(message: Message) {

        for (plugin in plugins) {

            if (plugin.handle(message)) {

                return

            }

        }

    }

}
