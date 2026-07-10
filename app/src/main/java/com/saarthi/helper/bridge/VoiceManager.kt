package com.saarthi.helper.bridge

import android.content.Context

class VoiceManager(
    private val context: Context
) {

    fun execute(
        command: HelperCommand
    ): Boolean {

        return when (command.action) {

            "voice.ping" -> true

            else -> false

        }

    }

}
