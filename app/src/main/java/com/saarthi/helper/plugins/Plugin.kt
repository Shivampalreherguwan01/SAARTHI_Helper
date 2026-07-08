package com.saarthi.helper.plugins

import com.saarthi.helper.model.Message

interface Plugin {

    fun start()

    fun stop()

    fun handle(message: Message): Boolean

}
