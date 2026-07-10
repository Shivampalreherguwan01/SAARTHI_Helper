package com.saarthi.helper.bridge

import android.content.Context

class AppManager(
    private val context: Context
) {

    fun execute(
        command: HelperCommand
    ): Boolean {

        return when (command.action) {

            "app.ping" -> true

            else -> false

        }

    }

}
