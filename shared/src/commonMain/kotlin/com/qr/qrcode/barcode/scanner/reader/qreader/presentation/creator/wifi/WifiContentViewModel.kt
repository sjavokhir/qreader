package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.wifi

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
            is WifiContentEvent.SelectAuthentication -> onValueChanged(authentication = event.authentication)
            is WifiContentEvent.HiddenChecked -> onValueChanged(isHidden = event.isHidden)
        }
    }

    private fun onValueChanged(
        networkName: String? = null,
        password: String? = null,
        authentication: WifiContentState.Authentication? = null,
        isHidden: Boolean? = null
    ) {
        stateData.update {
            val mNetworkName = networkName ?: it.networkName

            it.copy(
                networkName = mNetworkName,
                password = password ?: it.password,
                authentication = authentication ?: it.authentication,
                isHidden = isHidden ?: it.isHidden,
                isEnabled = mNetworkName.isNotEmpty()
            )
        }
    }
}