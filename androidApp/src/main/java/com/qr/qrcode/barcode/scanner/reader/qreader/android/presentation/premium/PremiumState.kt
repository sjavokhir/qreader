package com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.premium

import com.android.billingclient.api.ProductDetails

data class PremiumState(
    val productDetails: List<ProductDetailsModel> = emptyList(),
    val selectedProductId: String = Subscriptions.Monthly.productId
)

data class ProductDetailsModel(
    val productId: String,
    val name: String,
    val description: String,
    val formattedPrice: String,
    val offerToken: String,
    val productDetails: ProductDetails
)

enum class Subscriptions(val productId: String) {
    Weekly("subscription_weekly"),
    Monthly("subscription_monthly"),
    Anually("subscription_anually")
}

fun String.caption(): String {
    return when (this) {
        Subscriptions.Weekly.productId -> "/week"
        Subscriptions.Monthly.productId -> "/month"
        Subscriptions.Anually.productId -> "/year"
        else -> this
    }
}
