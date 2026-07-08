package com.saarthi.helper.security

import android.content.Context

object ModelLoader {

    fun load(
        context: Context,
        model: SpeakerModel
    ): Boolean {

        return try {

            val input =
                context.assets.open("speaker/model.onnx")

            val bytes =
                input.readBytes()

            input.close()

            model.load(bytes)

        } catch (e: Exception) {

            false

        }

    }

}
