package com.saarthi.helper.bridge

import android.content.Context

class CommandReceiver(
    context: Context
) {

    private val body =
        BodyManager(context)

    fun execute(
        command: HelperCommand
    ): Boolean {

        return body.execute(command)

    }

}
