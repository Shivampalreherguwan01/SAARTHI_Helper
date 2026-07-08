package com.saarthi.helper.security

import android.content.Context

class SpeakerModel(

    private val context: Context

) {

    fun createEmbedding(

        audioPath: String

    ): ByteArray {

        /*
         अभी Temporary Implementation है.

         बाद में यहाँ:
         - ONNX Runtime
         - Audio Preprocessing
         - ECAPA Model
         - Real Voice Embedding

         आएगा.
        */

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
