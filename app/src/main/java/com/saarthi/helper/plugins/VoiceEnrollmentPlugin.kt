package com.saarthi.helper.plugins

import com.saarthi.helper.model.Message
import com.saarthi.helper.security.SecurityManager

class VoiceEnrollmentPlugin : Plugin {

    override fun start() {
    }

    override fun stop() {
    }

    override fun handle(
        message: Message
    ): Boolean {

        if (message.type != "command") {
            return false
        }

        return when (message.command) {

            "enroll_voice" -> {

                if (message.data.isNotBlank()) {

                    SecurityManager
                        .voice()
                        .enroll(message.data)
                }

                true
            }

            "remove_voice" -> {

                SecurityManager
                    .voice()
                    .removeProfile()

                true
            }

            else -> false
        }
    }
}
