package com.saarthi.helper.bridge

import android.content.Context

class BodyManager(
    context: Context
) {

    private val appManager =
        AppManager(context)

    private val cameraManager =
        CameraManager(context)

    fun execute(
        command: HelperCommand
    ): Boolean {

        if (appManager.execute(command)) {
            return true
        }

        if (cameraManager.execute(command)) {
            return true
        }

        return when (command.action) {

            "helper.ping" -> true

            else -> false

        }

    }

}
