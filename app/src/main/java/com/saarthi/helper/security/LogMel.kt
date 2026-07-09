package com.saarthi.helper.security

import kotlin.math.ln
import kotlin.math.max

object LogMel {

    fun apply(
        mel: FloatArray
    ): FloatArray {

        val output =
            FloatArray(mel.size)

        for (i in mel.indices) {

            output[i] =
                ln(
                    max(
                        mel[i],
                        1e-6f
                    )
                )

        }

        return output

    }

}
