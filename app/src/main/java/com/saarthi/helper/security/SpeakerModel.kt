package com.saarthi.helper.security

import android.content.Context

class SpeakerModel(

    private val context: Context

) {

    private var modelLoaded = false

    fun load(

        modelBytes: ByteArray

    ): Boolean {

        /*
         अभी Placeholder है।

         भविष्य में यहाँ:
         ONNX Runtime Session बनेगा।
        */

        modelLoaded = modelBytes.isNotEmpty()

        return modelLoaded

    }

    fun createEmbedding(

        audioPath: String

    ): ByteArray {

        return audioPath.toByteArray()

    }

    fun verify(

        audioPath: String,

        savedEmbedding: ByteArray

    ): Boolean {

        val current =

            createEmbedding(audioPath)

        return current.contentEquals(

            savedEmbedding

        )

    }

}
