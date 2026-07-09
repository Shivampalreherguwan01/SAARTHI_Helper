package com.saarthi.helper.security

import kotlin.math.PI
import kotlin.math.cos

object WindowFunction {

    fun hamming(
        samples: FloatArray
    ) {

        val n =
            samples.size

        for (i in samples.indices) {

            val w =
                (0.54 -
                 0.46 *
                 cos(
                     2.0 * PI * i / (n - 1)
                 )).toFloat()

            samples[i] *= w

        }

    }

}
