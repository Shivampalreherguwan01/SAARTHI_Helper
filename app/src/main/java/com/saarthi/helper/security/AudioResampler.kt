package com.saarthi.helper.security

object AudioResampler {

    fun resample(

        pcm: FloatArray,

        inputRate: Int,

        outputRate: Int

    ): FloatArray {

        if (inputRate == outputRate) {
            return pcm
        }

        val ratio =
            outputRate.toFloat() / inputRate

        val outputLength =
            (pcm.size * ratio).toInt()

        val output =
            FloatArray(outputLength)

        for (i in output.indices) {

            val position =
                i / ratio

            val left =
                position.toInt()

            val right =
                minOf(
                    left + 1,
                    pcm.lastIndex
                )

            val alpha =
                position - left

            output[i] =
                pcm[left] * (1f - alpha) +
                pcm[right] * alpha

        }

        return output

    }

}
