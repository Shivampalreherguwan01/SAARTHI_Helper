package com.saarthi.helper.security

import android.content.Context
import java.io.File

class SpeakerDatabase(

    context: Context

) {

    private val profileFile =

        File(
            context.filesDir,
            "speaker_profile.bin"
        )

    fun exists(): Boolean {

        return profileFile.exists()

    }

    fun save(

        embedding: ByteArray

    ) {

        profileFile.writeBytes(
            embedding
        )

    }

    fun load(): ByteArray? {

        if (!exists()) {

            return null

        }

        return profileFile.readBytes()

    }

    fun clear() {

        if (exists()) {

            profileFile.delete()

        }

    }

}
