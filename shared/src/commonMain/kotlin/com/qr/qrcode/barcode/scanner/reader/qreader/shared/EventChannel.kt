package com.qr.qrcode.barcode.scanner.reader.qreader.shared

import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.LanguageType
import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.ThemeMode
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
object EventChannel {

    private val channel = Channel<Event>()

    fun sendEvent(event: Event) {
        channel.trySend(event)
    }

    fun receiveEvent(): Flow<Event> {
        return channel.receiveAsFlow()
    }
}

sealed class Event {
    object Idle : Event()

    data class ThemeModeChanged(val themeMode: ThemeMode) : Event()
    data class LanguageChanged(val language: LanguageType) : Event()
}