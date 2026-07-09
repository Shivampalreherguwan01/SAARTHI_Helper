package com.saarthi.helper.security

import kotlin.math.sqrt

object FeatureNormalizer {

    fun normalize(
        feature: FloatArray
    ): FloatArray {

        val output =
            FloatArray(feature.size)

        var mean = 0f

        for (v in feature) {
            mean += v
        }

        mean /= feature.size

        var variance = 0f

        for (v in feature) {

            val d = v - mean
            variance += d * d

        }

        variance /= feature.size

        val std =
            sqrt(variance + 1e-6f)

        for (i in feature.indices) {

            output[i] =
                (feature[i] - mean) / std

        }

        return output

    }

}
