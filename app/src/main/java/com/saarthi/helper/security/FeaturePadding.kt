package com.saarthi.helper.security

object FeaturePadding {

    private const val MAX_FRAMES = 360

    fun pad(
        frames: MutableList<FloatArray>
    ): Array<FloatArray> {

        val output =
            Array(MAX_FRAMES) {
                FloatArray(80)
            }

        val count =
            minOf(
                frames.size,
                MAX_FRAMES
            )

        for (i in 0 until count) {

            output[i] = frames[i]

        }

        return output

    }

}
