package com.saarthi.helper.security

import kotlin.math.ln
import kotlin.math.max

class MelFilterBank(

    private val melBins: Int = 80

) {

    fun apply(
        spectrum: FloatArray
    ): FloatArray {

        val result =
            FloatArray(melBins)

        val step =
            max(
                1,
                spectrum.size / melBins
            )

        for (i in 0 until melBins) {

            var sum = 0f

            val start =
                i * step

            val end =
                minOf(
                    start + step,
                    spectrum.size
                )

            for (j in start until end) {
                sum += spectrum[j]
            }

            result[i] =
                ln(1f + sum)

        }

        return result

    }

}
