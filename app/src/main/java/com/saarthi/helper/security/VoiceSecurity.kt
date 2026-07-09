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

        verifier.enable()

    }

    fun isEnabled(): Boolean {

        return database.exists()

    }

    fun enroll(
        audioPath: String
    ): Boolean {

        return verifier.enroll(audioPath)

    }

    fun verify(
        audioPath: String
    ): Boolean {

        return verifier.verify(audioPath)

    }

    fun removeProfile() {

        database.clear()

    }

}
