package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.wifi

import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.common.TopicModel
import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class WifiContentViewModel : KMMViewModel() {

    private val stateData = MutableStateFlow(viewModelScope, WifiContentState())

    @NativeCoroutinesState
    val state = stateData.asStateFlow()

    fun onEvent(event: WifiContentEvent) {
        when (event) {
            is WifiContentEvent.NetworkNameChanged -> onValueChanged(networkName = event.name)
            is WifiContentEvent.PasswordChanged -> onValueChanged(password = event.password)
            is WifiContentEvent.SelectType -> onTypeSelected(event.type)
        }
    }

    private fun onTypeSelected(type: TopicModel) {
        stateData.update {
            it.copy(
                selectedType = type,
                generateText = it.generateText()
            )
        }
    }

    private fun onValueChanged(
        networkName: String? = null,
        password: String? = null
    ) {
        stateData.update {
            val mNetworkName = networkName ?: it.networkName

            it.copy(
                networkName = mNetworkName,
                password = password ?: it.password,
                isEnabled = mNetworkName.isNotEmpty()
            )
        }
        stateData.update { it.copy(generateText = it.generateText()) }
    }

    private fun WifiContentState.generateText(): String {
        return buildString {
            append("WIFI:")
            append("S:$networkName;")
            append("P:$password;")
            append("T:${selectedType.title};")
        }
    }
}