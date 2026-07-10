package com.saarthi.helper.bridge

import android.content.Context

class BodyManager(
    private val context: Context
) {

    fun execute(
        command: HelperCommand
    ): Boolean {

        return when (command.action) {

            "helper.ping" -> true

            else -> false
        }

    }

}
