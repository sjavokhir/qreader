package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.language

import com.qr.qrcode.barcode.scanner.reader.qreader.data.datastore.AppStore
import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.LanguageType
import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmm.viewmodel.coroutineScope
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class LanguageViewModel : KMMViewModel(), KoinComponent {

    private val appStore by inject<AppStore>()

    private val stateData = MutableStateFlow(viewModelScope, LanguageState())

    @NativeCoroutinesState
    val state = stateData.asStateFlow()

    init {
        viewModelScope.coroutineScope.launch {
            val languages = listOf(
                LanguageType.English,
                LanguageType.Uzbek,
                LanguageType.Arabic,
                LanguageType.Turkish,
                LanguageType.German,
                LanguageType.French,
                LanguageType.Japanese,
                LanguageType.Korean,
                LanguageType.Portuguese,
                LanguageType.Spanish,
                LanguageType.Italian,
                LanguageType.Russian,
                LanguageType.Chinese
            )

            stateData.update {
                it.copy(
                    selectedLanguage = appStore.getSelectedLanguage(),
                    languages = languages
                )
            }
        }
    }

    fun onEvent(event: LanguageEvent) {
        when (event) {
            is LanguageEvent.SelectLanguage -> setSelectedLanguage(event.language)
        }
    }

    private fun setSelectedLanguage(language: LanguageType) {
        appStore.setSelectedLanguage(language)

        stateData.update {
            it.copy(selectedLanguage = language)
        }
    }
}