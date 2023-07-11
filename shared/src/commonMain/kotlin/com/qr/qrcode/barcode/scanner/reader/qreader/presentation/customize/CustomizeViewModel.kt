package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.customize

import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.QRCornerMode
import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.QRDotMode
import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.QRPatternMode
import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent

class CustomizeViewModel : KMMViewModel(), KoinComponent {

    private val stateData = MutableStateFlow(viewModelScope, CustomizeState())

    @NativeCoroutinesState
    val state = stateData.asStateFlow()

    init {
        getAllStyles()
    }

    fun onEvent(event: CustomizeEvent) {
        when (event) {
            is CustomizeEvent.Customize -> onCustomize(event.state)
            is CustomizeEvent.SelectPattern -> onPatternSelected(event.pattern)
            is CustomizeEvent.SelectCorner -> onCornerSelected(event.corner)
            is CustomizeEvent.SelectDot -> onDotSelected(event.dot)
            is CustomizeEvent.SelectColor -> onColorSelected(event.hex.uppercase())
            is CustomizeEvent.SelectLogo -> onLogoSelected(event.logo)
            is CustomizeEvent.ShowColorPicker -> onColorPickerShowed(event.colorPickerType)
            CustomizeEvent.DismissColorPicker -> onColorPickerDismissed()
            is CustomizeEvent.ShowHidePreview -> onPreviewShowed(event.show)
        }
    }

    private fun getAllStyles() {
        val patterns = listOf(
            QRPatternMode.Default, QRPatternMode.Rounded, QRPatternMode.ExtraRounded,
            QRPatternMode.Circle, QRPatternMode.CirclePadding, QRPatternMode.Horizontal,
            QRPatternMode.Vertical, QRPatternMode.Rhombus, QRPatternMode.Star,
            QRPatternMode.Classy, QRPatternMode.ClassyRounded,
        )
        val corners = listOf(
            QRCornerMode.Default, QRCornerMode.Rounded, QRCornerMode.Circle,
            QRCornerMode.Two, QRCornerMode.Three, QRCornerMode.Dots,
            QRCornerMode.DotsPadding, QRCornerMode.Rhombus
        )
        val dots = listOf(
            QRDotMode.Default, QRDotMode.Rounded, QRDotMode.Circle,
            QRDotMode.Rhombus, QRDotMode.CornerTwo, QRDotMode.CornerThree,
            QRDotMode.Dots, QRDotMode.DotsPadding, QRDotMode.Horizontal,
            QRDotMode.Vertical
        )

        stateData.update {
            it.copy(
                patterns = patterns,
                corners = corners,
                dots = dots,
                logos = logos
            )
        }
    }

    private fun onCustomize(state: CustomizeState) {
        stateData.update {
            it.copy(
                selectedPattern = state.selectedPattern,
                selectedCorner = state.selectedCorner,
                selectedDot = state.selectedDot,
                patternDotHex = state.patternDotHex,
                patternBackgroundHex = state.patternBackgroundHex,
                frameHex = state.frameHex,
                frameDotHex = state.frameDotHex,
                selectedLogo = state.selectedLogo,
                ownLogoPath = state.ownLogoPath
            )
        }
    }

    private fun onPatternSelected(pattern: QRPatternMode) {
        stateData.update { it.copy(selectedPattern = pattern) }
    }

    private fun onCornerSelected(corner: QRCornerMode) {
        stateData.update { it.copy(selectedCorner = corner) }
    }

    private fun onDotSelected(dot: QRDotMode) {
        stateData.update { it.copy(selectedDot = dot) }
    }

    private fun onColorSelected(hex: String) {
        stateData.update {
            when (it.colorPickerType) {
                ColorPickerType.PatternDotColor -> {
                    it.copy(
                        showColorPicker = false,
                        patternDotHex = hex
                    )
                }

                ColorPickerType.PatternBackgroundColor -> {
                    it.copy(
                        showColorPicker = false,
                        patternBackgroundHex = hex
                    )
                }

                ColorPickerType.FrameColor -> {
                    it.copy(
                        showColorPicker = false,
                        frameHex = hex
                    )
                }

                ColorPickerType.FrameDotColor -> {
                    it.copy(
                        showColorPicker = false,
                        frameDotHex = hex
                    )
                }
            }
        }
    }

    private fun onLogoSelected(logo: String) {
        stateData.update {
            it.copy(
                selectedLogo = if (logo == it.selectedLogo) "" else logo
            )
        }
    }

    private fun onColorPickerShowed(colorPickerType: ColorPickerType) {
        stateData.update {
            it.copy(
                showColorPicker = true,
                colorPickerType = colorPickerType
            )
        }
    }

    private fun onColorPickerDismissed() {
        stateData.update { it.copy(showColorPicker = false) }
    }

    private fun onPreviewShowed(show: Boolean) {
        stateData.update { it.copy(showPreview = show) }
    }
}

private val logos: List<String>
    get() = listOf(
        "ic_logo_behance",
        "ic_logo_bitcoin",
        "ic_logo_discord",
        "ic_logo_dribbble",
        "ic_logo_facebook",
        "ic_logo_figma",
        "ic_logo_github",
        "ic_logo_google",
        "ic_logo_instagram",
        "ic_logo_linkedin",
        "ic_logo_medium",
        "ic_logo_messenger",
        "ic_logo_patreon",
        "ic_logo_pinterest",
        "ic_logo_quora",
        "ic_logo_reddit",
        "ic_logo_spotify",
        "ic_logo_stack_overflow",
        "ic_logo_telegram",
        "ic_logo_tiktok",
        "ic_logo_twitch",
        "ic_logo_twitter",
        "ic_logo_whatsapp",
        "ic_logo_youtube",
        "ic_logo_play_google",
        "ic_logo_app_store",
        "ic_logo_share",
        "ic_logo_wifi"
    )