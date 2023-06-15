package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.generateContents

import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.GenerateType
import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmm.viewmodel.coroutineScope
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GenerateContentsViewModel : KMMViewModel() {

    private val stateData = MutableStateFlow(viewModelScope, GenerateContentsState())

    @NativeCoroutinesState
    val state = stateData.asStateFlow()

    init {
        getGenerateContents()
    }

    private fun getGenerateContents() {
        stateData.update { it.copy(isLoading = true) }

        viewModelScope.coroutineScope.launch {
            val contents = buildList {
                add(GenerateType.Text)
                add(GenerateType.Website)
                add(GenerateType.Sms)
                add(GenerateType.PhoneNumber)
                add(GenerateType.EmailAddress)
                add(GenerateType.Wifi)
                add(GenerateType.CalendarEvent)
                add(GenerateType.ContactVCard)
                add(GenerateType.BusinessVCard)
                add(GenerateType.DriverLicense)
                add(GenerateType.Location)
                add(GenerateType.Youtube)
                add(GenerateType.WhatsApp)
                add(GenerateType.Instagram)
                add(GenerateType.Facebook)
                add(GenerateType.Twitter)
                add(GenerateType.TikTok)
                add(GenerateType.Telegram)
                add(GenerateType.Twitch)
                add(GenerateType.LinkedIn)
                add(GenerateType.Github)
                add(GenerateType.Pinterest)
                add(GenerateType.Tumblr)
            }.groupBy {
                it.headerType
            }

            stateData.update {
                it.copy(
                    contents = contents,
                    isLoading = false
                )
            }
        }
    }
}