package com.saarthi.helper.security

import android.content.Context

class VoiceSecurity(

    context: Context

) {

    private val database =
        SpeakerDatabase(context)

    private val verifier =
        SpeakerVerifier(context)

    init {

        if (database.exists()) {

            verifier.enable()

        }

    }

    fun isEnabled(): Boolean {

        return verifier.isEnabled()

    }

    fun enroll(

        audioPath: String

    ): Boolean {

        val embedding =

            verifier.enroll(audioPath)

                ?: return false

        database.save(

            embedding

        )

        verifier.enable()

        return true

    }

    fun verify(

        audioPath: String

    ): Boolean {

        val embedding =

            database.load()

        return verifier.verify(

            audioPath,

            embedding

        )

    }

    fun removeProfile() {

        verifier.disable()

        database.clear()

    }

}
