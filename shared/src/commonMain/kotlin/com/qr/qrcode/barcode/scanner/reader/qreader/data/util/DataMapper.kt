package com.qr.qrcode.barcode.scanner.reader.qreader.data.util

interface DataMapper<T, S> {
    fun T.mapToDomain(): S
}

fun <T : DataMapper<T, S>, S> T.mapToDomain(): S = this.mapToDomain()