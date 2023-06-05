package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.onBoarding

sealed class OnBoardingEvent {
    object Start : OnBoardingEvent()
    object Idle : OnBoardingEvent()
}