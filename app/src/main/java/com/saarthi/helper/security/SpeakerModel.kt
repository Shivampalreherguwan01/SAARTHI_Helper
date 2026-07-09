package com.saarthi.helper.security

import ai.onnxruntime.OnnxTensor
import ai.onnxruntime.OrtEnvironment
import ai.onnxruntime.OrtSession

class SpeakerModel {

    private val env =
        OrtEnvironment.getEnvironment()

    private var session: OrtSession? = null

    fun load(model: ByteArray): Boolean {

        return try {

            session =
                env.createSession(model)

            true

        } catch (e: Exception) {

            false

        }

    }

    fun embedding(
        feature: Array<Array<FloatArray>>
    ): FloatArray? {

        val s = session ?: return null

        return try {

            val tensor =
                OnnxTensor.createTensor(
                    env,
                    feature
                )

            val result =
                s.run(
                    mapOf(
                        "feature" to tensor
                    )
                )

            @Suppress("UNCHECKED_CAST")
            val output =
                result[0].value as Array<FloatArray>

            output[0]

        } catch (e: Exception) {

            null

        }

    }

}
