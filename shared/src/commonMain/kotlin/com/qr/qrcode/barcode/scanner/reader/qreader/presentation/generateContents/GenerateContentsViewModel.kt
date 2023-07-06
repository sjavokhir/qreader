package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.generateContents

import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.GenerateMode
import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmm.viewmodel.coroutineScope
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class GenerateContentsViewModel : KMMViewModel(), KoinComponent {

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
                add(GenerateMode.Text)
                add(GenerateMode.Website)
                add(GenerateMode.Sms)
                add(GenerateMode.PhoneNumber)
                add(GenerateMode.EmailAddress)
                add(GenerateMode.Wifi)
                add(GenerateMode.ContactVCard)
                add(GenerateMode.CalendarEvent)
                add(GenerateMode.BizCard)
                add(GenerateMode.BusinessVCard)
                add(GenerateMode.Location)
                add(GenerateMode.Youtube)
                add(GenerateMode.WhatsApp)
                add(GenerateMode.Instagram)
                add(GenerateMode.Facebook)
                add(GenerateMode.Twitter)
                add(GenerateMode.TikTok)
                add(GenerateMode.Telegram)
                add(GenerateMode.VKontakte)
                add(GenerateMode.Twitch)
                add(GenerateMode.LinkedIn)
                add(GenerateMode.Github)
                add(GenerateMode.Medium)
                add(GenerateMode.Dribbble)
                add(GenerateMode.Behance)
            }.groupBy {
                it.header
            }

            stateData.update {
                it.copy(
                    contents = contents,
                    isLoading = false,
                )
            }
        }
    }
}