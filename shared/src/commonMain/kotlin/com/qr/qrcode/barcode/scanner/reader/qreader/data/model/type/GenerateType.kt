package com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type

import com.qr.qrcode.barcode.scanner.reader.qreader.core.helpers.StringRes

enum class GenerateType(
    val title: String,
    val icon: String,
    val isPremium: Boolean,
    val headerType: GenerateHeaderType
) {
    Text(StringRes.text, "ic_qr_text", false, GenerateHeaderType.Web),
    Website(StringRes.website, "ic_qr_website", false, GenerateHeaderType.Web),
    Sms(StringRes.sms, "ic_qr_sms", false, GenerateHeaderType.Communication),
    PhoneNumber(StringRes.phoneNumber, "ic_qr_phone", false, GenerateHeaderType.Communication),
    EmailAddress(StringRes.emailAddress, "ic_qr_email", false, GenerateHeaderType.Communication),
    Wifi(StringRes.wifi, "ic_qr_wifi", false, GenerateHeaderType.Other),
    CalendarEvent(StringRes.calendarEvent, "ic_qr_event", false, GenerateHeaderType.Other),
    ContactVCard(StringRes.contactVCard, "ic_qr_contact", false, GenerateHeaderType.Other),
    BusinessVCard(StringRes.businessVCard, "ic_qr_business", true, GenerateHeaderType.Other),
    DriverLicense(StringRes.driverLicense, "ic_qr_driver_license", true, GenerateHeaderType.Other),
    Location(StringRes.location, "ic_qr_location", true, GenerateHeaderType.Other),
    Youtube(StringRes.youtube, "ic_qr_youtube", true, GenerateHeaderType.SocialMedia),
    WhatsApp(StringRes.whatsApp, "ic_qr_whatsapp", true, GenerateHeaderType.SocialMedia),
    Instagram(StringRes.instagram, "ic_qr_instagram", true, GenerateHeaderType.SocialMedia),
    Facebook(StringRes.facebook, "ic_qr_facebook", true, GenerateHeaderType.SocialMedia),
    Twitter(StringRes.twitter, "ic_qr_twitter", true, GenerateHeaderType.SocialMedia),
    TikTok(StringRes.tiktok, "ic_qr_tiktok", true, GenerateHeaderType.SocialMedia),
    Telegram(StringRes.telegram, "ic_qr_telegram", true, GenerateHeaderType.SocialMedia),
    VKontakte(StringRes.vkontakte, "ic_qr_vkontakte", true, GenerateHeaderType.SocialMedia),
    Twitch(StringRes.twitch, "ic_qr_twitch", true, GenerateHeaderType.SocialMedia),
    LinkedIn(StringRes.linkedin, "ic_qr_linkedin", true, GenerateHeaderType.SocialMedia),
    Github(StringRes.github, "ic_qr_github", true, GenerateHeaderType.SocialMedia),
    Medium(StringRes.medium, "ic_qr_medium", true, GenerateHeaderType.SocialMedia),
    Dribbble(StringRes.dribbble, "ic_qr_dribbble", true, GenerateHeaderType.SocialMedia),
    Behance(StringRes.behance, "ic_qr_behance", true, GenerateHeaderType.SocialMedia),
}

enum class GenerateHeaderType {
    Web,
    Communication,
    SocialMedia,
    Other
}