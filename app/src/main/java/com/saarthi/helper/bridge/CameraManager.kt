package com.saarthi.helper.bridge

import android.content.Context

class CameraManager(
    private val context: Context
) {

    fun execute(
        command: HelperCommand
    ): Boolean {

        return when (command.action) {

            "camera.ping" -> true

            else -> false

        }

    }

}
