package com.saarthi.helper.security

import android.content.Context

class SpeakerVerifier(

    private val context: Context

) {

    private val model =

        SpeakerModel(context)

    private var enabled = false

    fun enable() {

        enabled = true

    }

    fun disable() {

        enabled = false

    }

    fun isEnabled(): Boolean {

        return enabled

    }

    fun enroll(

        audioPath: String

    ): ByteArray? {

        return model.createEmbedding(audioPath)

    }

    fun verify(

        audioPath: String,

        savedEmbedding: ByteArray?

    ): Boolean {

        if (!enabled) {

            return true

        }

        if (savedEmbedding == null) {

            return false

        }

        return model.verify(

            audioPath,

            savedEmbedding

        )

    }

}
