package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.wifi

import com.qr.qrcode.barcode.scanner.reader.qreader.core.extensions.tryCatch
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
            is WifiContentEvent.Encoded -> onEncoded(event.value)
            is WifiContentEvent.NetworkNameChanged -> onValueChanged(networkName = event.name)
            is WifiContentEvent.PasswordChanged -> onValueChanged(password = event.password)
            is WifiContentEvent.SelectAuthentication -> onValueChanged(authentication = event.authentication)
            is WifiContentEvent.HiddenChecked -> onValueChanged(isHidden = event.isHidden)
        }
    }

    private fun onEncoded(value: String) {
        if (state.value.isSetEncoded) return

        val content = value.toWifiContent() ?: return

        onValueChanged(
            networkName = content.networkName,
            password = content.password,
            authentication = content.authentication,
            isHidden = content.isHidden
        )
    }

    private fun onValueChanged(
        networkName: String? = null,
        password: String? = null,
        authentication: WifiContentState.Authentication? = null,
        isHidden: Boolean? = null
    ) {
        stateData.update {
            val mNetworkName = networkName ?: it.networkName
            val mPassword = password ?: it.password

            it.copy(
                networkName = mNetworkName,
                password = mPassword,
                authentication = authentication ?: it.authentication,
                isHidden = isHidden ?: it.isHidden,
                isEnabled = when (authentication ?: it.authentication) {
                    WifiContentState.Authentication.WEP -> {
                        mNetworkName.isNotEmpty() && mPassword.isNotEmpty()
                    }

                    WifiContentState.Authentication.WPA_WPA2 -> {
                        mNetworkName.isNotEmpty() && mPassword.length >= 8
                    }

                    WifiContentState.Authentication.OPEN -> {
                        mNetworkName.isNotEmpty()
                    }
                },
                isSetEncoded = true
            )
        }
    }
}