package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.language

import com.qr.qrcode.barcode.scanner.reader.qreader.data.datastore.AppStore
import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.LanguageType
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.base.BaseViewModel
import com.rickclephas.kmm.viewmodel.coroutineScope
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class LanguageViewModel : BaseViewModel<LanguageState, LanguageEvent>(LanguageState()),
    KoinComponent {

    private val appStore by inject<AppStore>()

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

    override fun onEvent(event: LanguageEvent) {
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