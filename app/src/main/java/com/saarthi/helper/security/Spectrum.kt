package com.saarthi.helper.security

import kotlin.math.sqrt

object Spectrum {

    fun power(
        real: FloatArray,
        imag: FloatArray
    ): FloatArray {

        val output =
            FloatArray(real.size / 2)

        for (i in output.indices) {

            output[i] =
                sqrt(
                    real[i] * real[i] +
                    imag[i] * imag[i]
                )

        }

        return output

    }

}
