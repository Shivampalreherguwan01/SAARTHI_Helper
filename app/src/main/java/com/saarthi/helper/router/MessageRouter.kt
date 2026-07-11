package com.saarthi.helper.router

import android.content.Context
import com.saarthi.helper.bridge.CommandReceiver
import com.saarthi.helper.bridge.HelperCommand
import com.saarthi.helper.model.Message
import com.saarthi.helper.plugins.PluginManager

class MessageRouter(
    context: Context
) {

    private lateinit var plugins: PluginManager

    private val commandReceiver =
        CommandReceiver(context.applicationContext)

    fun attach(
        pluginManager: PluginManager
    ) {
        plugins = pluginManager
    }

    fun dispatch(
        message: Message
    ) {

        if (message.type == "command") {

            val action =
                message.command.ifBlank {
                    message.text
                }

            if (action.isNotBlank()) {

                val command =
                    HelperCommand(
                        id = "",
                        action = action,
                        params = mapOf(
                            "data" to message.data,
                            "text" to message.text
                        ),
                        source = "saarthi_os"
                    )

                if (commandReceiver.execute(command)) {
                    return
                }
            }
        }

        if (::plugins.isInitialized) {
            plugins.dispatch(message)
        }
    }
}
