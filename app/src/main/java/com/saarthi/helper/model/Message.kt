package com.saarthi.helper.model

import org.json.JSONObject

data class Message(

    val type: String,

    val text: String = "",

    val data: String = "",

    val command: String = ""

) {

    fun toJson(): String {

        val json = JSONObject()

        json.put("type", type)

        json.put("text", text)

        json.put("data", data)

        json.put("command", command)

        return json.toString()

    }

    companion object {

        fun fromJson(raw: String): Message {

            val json = JSONObject(raw)

            return Message(

                type = json.optString("type"),

                text = json.optString("text"),

                data = json.optString("data"),

                command = json.optString("command")

            )

        }

    }

}
