package com.saarthi.helper.security

import android.content.Context

class SpeakerVerifier(
    private val context: Context
) {

    private val model =
        SpeakerModel()

    private val extractor =
        AudioFeatureExtractor

    private var enabled = false

    init {

        ModelLoader.load(
            context,
            model
        )

    }

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
    ): Boolean {

        val feature =
            extractor.extract(audioPath)

        val embedding =
            model.embedding(feature)

        return embedding != null

    }

    fun verify(
        audioPath: String
    ): Boolean {

        if (!enabled) {
            return true
        }

        val feature =
            extractor.extract(audioPath)

        val embedding =
            model.embedding(feature)

        return embedding != null

    }

}
