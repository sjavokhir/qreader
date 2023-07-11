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
        tryCatch {
            val wifiRegex = Regex("""WIFI:S:(.*?);T:(.*?);P:(.*?);H:(.*?);""")
            val matchResult = wifiRegex.find(value)

            matchResult?.groupValues?.let { groups ->
                val networkName = groups[1]
                val password = groups[3]
                val isHidden = groups[4].toBooleanStrictOrNull() ?: false

                onValueChanged(
                    networkName,
                    password,
                    WifiContentState.Authentication.WEP,
                    isHidden
                )
            }
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
            val mPassword = password ?: it.password

            it.copy(
                networkName = mNetworkName,
                password = mPassword,
                authentication = authentication ?: it.authentication,
                isHidden = isHidden ?: it.isHidden,
                isEnabled = when (it.authentication) {
                    WifiContentState.Authentication.WEP -> {
                        mNetworkName.isNotEmpty() && mPassword.isNotEmpty()
                    }

                    WifiContentState.Authentication.WPA_WPA2 -> {
                        mNetworkName.isNotEmpty() && mPassword.length >= 8
                    }

                    WifiContentState.Authentication.OPEN -> {
                        mNetworkName.isNotEmpty()
                    }
                }
            )
        }
    }
}