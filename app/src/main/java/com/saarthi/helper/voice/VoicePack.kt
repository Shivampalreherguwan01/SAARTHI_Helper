package com.saarthi.helper.voice

import java.util.Locale

data class VoicePack(
    val id: String,
    val name: String,
    val locale: Locale,
    val speed: Float,
    val pitch: Float
)

object VoicePacks {

    val DEFAULT = VoicePack(
        "default",
        "Default",
        Locale("hi", "IN"),
        1.0f,
        1.0f
    )

    val MALE = VoicePack(
        "male",
        "Male",
        Locale("en", "US"),
        0.95f,
        0.90f
    )

    val FEMALE = VoicePack(
        "female",
        "Female",
        Locale("en", "US"),
        1.0f,
        1.15f
    )

    val DEEP = VoicePack(
        "deep",
        "Deep",
        Locale("en", "US"),
        0.80f,
        0.70f
    )

    val SOFT = VoicePack(
        "soft",
        "Soft",
        Locale("en", "US"),
        1.05f,
        1.25f
    )

    val ALL = listOf(
        DEFAULT,
        MALE,
        FEMALE,
        DEEP,
        SOFT
    )
}
