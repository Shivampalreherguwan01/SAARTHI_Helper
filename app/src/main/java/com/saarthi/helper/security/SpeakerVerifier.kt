package com.saarthi.helper.security

import android.content.Context
import java.nio.ByteBuffer
import java.nio.ByteOrder
import kotlin.math.sqrt

class SpeakerVerifier(
    private val context: Context
) {

    private val model = SpeakerModel()

    private val extractor = AudioFeatureExtractor

    private val database = SpeakerDatabase(context)

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

        val feature = arrayOf(
            extractor.extract(audioPath)
        )

        val embedding =
            model.embedding(feature)
                ?: return false

        if (embedding.size != 192) {
            return false
        }

        database.save(
            floatArrayToBytes(embedding)
        )

        return true
    }

    fun verify(
        audioPath: String
    ): Boolean {

        if (!enabled) {
            return true
        }

        val savedBytes =
            database.load()
                ?: return false

        val savedEmbedding =
            bytesToFloatArray(savedBytes)

        if (savedEmbedding.size != 192) {
            return false
        }

        val feature = arrayOf(
            extractor.extract(audioPath)
        )

        val currentEmbedding =
            model.embedding(feature)
                ?: return false

        if (currentEmbedding.size != 192) {
            return false
        }

        val similarity =
            cosineSimilarity(
                savedEmbedding,
                currentEmbedding
            )

        return similarity >= 0.70f
    }

    private fun cosineSimilarity(
        a: FloatArray,
        b: FloatArray
    ): Float {

        if (a.size != b.size) {
            return -1f
        }

        var dot = 0f
        var normA = 0f
        var normB = 0f

        for (i in a.indices) {

            dot += a[i] * b[i]

            normA += a[i] * a[i]

            normB += b[i] * b[i]
        }

        if (
            normA <= 0f ||
            normB <= 0f
        ) {
            return -1f
        }

        return (
            dot /
            (
                sqrt(normA) *
                sqrt(normB)
            )
        )
    }

    private fun floatArrayToBytes(
        values: FloatArray
    ): ByteArray {

        val buffer =
            ByteBuffer
                .allocate(values.size * 4)
                .order(ByteOrder.LITTLE_ENDIAN)

        for (value in values) {
            buffer.putFloat(value)
        }

        return buffer.array()
    }

    private fun bytesToFloatArray(
        bytes: ByteArray
    ): FloatArray {

        if (bytes.size % 4 != 0) {
            return FloatArray(0)
        }

        val buffer =
            ByteBuffer
                .wrap(bytes)
                .order(ByteOrder.LITTLE_ENDIAN)

        val output =
            FloatArray(bytes.size / 4)

        for (i in output.indices) {
            output[i] = buffer.float
        }

        return output
    }
}
