package com.saarthi.helper.security

import android.content.Context

class SpeakerVerifier(
    private val context: Context
) {

    private var enabled = false

    fun enable() {
        enabled = true
    }

    fun disable() {
        enabled = false
    }

    fun isEnabled(): Boolean {
        return enabled
    }

    fun enroll(audioPath: String): Boolean {

        // आगे यहाँ ONNX model से voice embedding बनेगी

        return true
    }

    fun verify(audioPath: String): Boolean {

        if (!enabled) {
            return true
        }

        // आगे यहाँ Speaker Verification होगी

        return true
    }

}
