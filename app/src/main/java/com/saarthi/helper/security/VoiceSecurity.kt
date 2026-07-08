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

    fun enroll(audioPath: String): Boolean {

        val ok =
            verifier.enroll(audioPath)

        if (!ok) {

            return false

        }

        verifier.enable()

        return true

    }

    fun verify(audioPath: String): Boolean {

        return verifier.verify(audioPath)

    }

    fun removeProfile() {

        verifier.disable()

        database.clear()

    }

}
