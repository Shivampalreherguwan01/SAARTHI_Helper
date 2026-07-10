package com.saarthi.helper.bridge

data class HelperCommand(

    val id: String,

    val action: String,

    val params: Map<String, String> = emptyMap(),

    val source: String

)
