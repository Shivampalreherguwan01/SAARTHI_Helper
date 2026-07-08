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

        when (message.text) {

            "enroll_voice" -> {

                if (message.data.isNotEmpty()) {

                    SecurityManager
                        .voice()
                        .enroll(message.data)

                }

                return true
            }

            "remove_voice" -> {

                SecurityManager
                    .voice()
                    .removeProfile()

                return true
            }

        }

        return false
    }

}
