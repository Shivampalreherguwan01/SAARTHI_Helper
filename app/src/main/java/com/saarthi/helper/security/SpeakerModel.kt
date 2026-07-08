package com.saarthi.helper.security

import ai.onnxruntime.OrtEnvironment
import ai.onnxruntime.OrtSession

class SpeakerModel {

    private var environment: OrtEnvironment? = null

    private var session: OrtSession? = null

    fun load(modelBytes: ByteArray): Boolean {

        return try {

            environment = OrtEnvironment.getEnvironment()

            session = environment!!.createSession(modelBytes)

            true

        } catch (e: Exception) {

            false

        }

    }

    fun loaded(): Boolean {

        return session != null

    }

    fun session(): OrtSession? {

        return session

    }

}
