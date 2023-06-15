package com.qr.qrcode.barcode.scanner.reader.qreader.core.extensions

fun String.isUrlValid(): Boolean {
    val regex =
        "^(http://www\\.|https://www\\.|http://|https://)?[-a-zA-Z0-9@:%._+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b[-a-zA-Z0-9()@:%_+.~#?&/=]*$"
    val urlRegex = regex.toRegex(RegexOption.IGNORE_CASE)
    return urlRegex.matches(this)
}