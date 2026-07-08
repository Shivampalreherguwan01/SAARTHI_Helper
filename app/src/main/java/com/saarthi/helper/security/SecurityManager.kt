package com.saarthi.helper.security

import android.content.Context

object SecurityManager {

    private lateinit var voiceSecurity: VoiceSecurity

    fun initialize(
        context: Context
    ) {

        voiceSecurity =
            VoiceSecurity(context)

    }

    fun voice(): VoiceSecurity {

        return voiceSecurity

    }

}
