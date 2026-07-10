package com.saarthi.helper.bridge

class CommandReceiver {

    fun execute(
        command: HelperCommand
    ): Boolean {

        return when (command.action) {

            "helper.ping" -> {

                true

            }

            else -> {

                false

            }

        }

    }

}
