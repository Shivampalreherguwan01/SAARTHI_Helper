package com.saarthi.helper.security

object AudioFeatureExtractor {

    fun extract(
        audioPath: String
    ): Array<FloatArray> {

        val pcm =
            AudioLoader.load(audioPath)

        val audio =
            AudioResampler.resample(
                pcm,
                16000,
                16000
            )

        val frames =
            AudioFramer.frame(audio)

        val features =
            mutableListOf<FloatArray>()

        for (frame in frames) {

            WindowFunction.hamming(frame)

            val real =
                frame.copyOf()

            val imag =
                FloatArray(real.size)

            FFT(real.size)
                .transform(real, imag)

            val spectrum =
                Spectrum.power(real, imag)

            val mel =
                MelFilterBank()
                    .apply(spectrum)

            val logMel =
                LogMel.apply(mel)

            val normalized =
                FeatureNormalizer
                    .normalize(logMel)

            features.add(normalized)

        }

        return FeaturePadding.pad(features)

    }

}
