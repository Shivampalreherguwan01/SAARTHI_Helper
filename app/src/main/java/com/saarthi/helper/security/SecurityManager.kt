package com.saarthi.helper.security

import android.content.Context

object SecurityManager {

    private lateinit var verifier: SpeakerVerifier

    fun initialize(context: Context) {

        verifier = SpeakerVerifier(context)

    }

    fun voice(): SpeakerVerifier {

        return verifier

    }

}
