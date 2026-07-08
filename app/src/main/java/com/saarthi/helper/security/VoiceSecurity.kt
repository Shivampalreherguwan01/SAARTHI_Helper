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

        verifier.initialize()

    }

    fun isEnabled(): Boolean {

        return database.exists()

    }

    fun enroll(audioPath: String): Boolean {

        val ok = verifier.enroll(audioPath)

        if (!ok) return false

        /*
         Next:
         Save embedding in database.
         */

        return true

    }

    fun verify(audioPath: String): Boolean {

        if (!database.exists()) {

            return true

        }

        return verifier.verify(audioPath)

    }

    fun removeProfile() {

        database.clear()

    }

}
