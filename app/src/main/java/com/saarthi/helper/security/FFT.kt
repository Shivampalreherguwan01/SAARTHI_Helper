package com.saarthi.helper.security

import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

class FFT(

    private val size: Int

) {

    fun transform(

        real: FloatArray,

        imag: FloatArray

    ) {

        var j = 0

        for (i in 0 until size) {

            if (i < j) {

                val tr = real[i]
                real[i] = real[j]
                real[j] = tr

                val ti = imag[i]
                imag[i] = imag[j]
                imag[j] = ti

            }

            var m = size shr 1

            while (j >= m && m >= 2) {

                j -= m
                m = m shr 1

            }

            j += m

        }

        var len = 2

        while (len <= size) {

            val angle =
                (-2.0 * PI / len)

            val wlenCos =
                cos(angle).toFloat()

            val wlenSin =
                sin(angle).toFloat()

            var i = 0

            while (i < size) {

                var wCos = 1f
                var wSin = 0f

                for (k in 0 until len / 2) {

                    val uReal =
                        real[i + k]

                    val uImag =
                        imag[i + k]

                    val vReal =
                        real[i + k + len / 2] * wCos -
                        imag[i + k + len / 2] * wSin

                    val vImag =
                        real[i + k + len / 2] * wSin +
                        imag[i + k + len / 2] * wCos

                    real[i + k] =
                        uReal + vReal

                    imag[i + k] =
                        uImag + vImag

                    real[i + k + len / 2] =
                        uReal - vReal

                    imag[i + k + len / 2] =
                        uImag - vImag

                    val nextCos =
                        wCos * wlenCos -
                        wSin * wlenSin

                    val nextSin =
                        wCos * wlenSin +
                        wSin * wlenCos

                    wCos = nextCos
                    wSin = nextSin

                }

                i += len

            }

            len = len shl 1

        }

    }

}
