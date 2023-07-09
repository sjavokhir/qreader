package com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type

import com.qr.qrcode.barcode.scanner.reader.qreader.core.helpers.StringRes

enum class GenerateMode(
    val title: String,
    val icon: String,
    val isPremium: Boolean,
    val header: GenerateHeader
) {
    Text(StringRes.text, "ic_qr_text", false, GenerateHeader.Web),
    Website(StringRes.website, "ic_qr_website", false, GenerateHeader.Web),
    Sms(StringRes.sms, "ic_qr_sms", false, GenerateHeader.Communication),
    PhoneNumber(StringRes.phoneNumber, "ic_qr_phone", false, GenerateHeader.Communication),
    EmailAddress(StringRes.emailAddress, "ic_qr_email", false, GenerateHeader.Communication),
    Wifi(StringRes.wifi, "ic_qr_wifi", false, GenerateHeader.Other),
    ContactVCard(StringRes.contactVCard, "ic_qr_contact", false, GenerateHeader.Other),
    CalendarEvent(StringRes.calendarEvent, "ic_qr_event", true, GenerateHeader.Other),
    BizCard(StringRes.bizCard, "ic_qr_biz", true, GenerateHeader.Other),
    BusinessVCard(StringRes.businessVCard, "ic_qr_business", true, GenerateHeader.Other),
    Location(StringRes.location, "ic_qr_location", true, GenerateHeader.Other),
    Youtube(StringRes.youtube, "ic_qr_youtube", true, GenerateHeader.SocialMedia),
    WhatsApp(StringRes.whatsApp, "ic_qr_whatsapp", true, GenerateHeader.SocialMedia),
    Instagram(StringRes.instagram, "ic_qr_instagram", true, GenerateHeader.SocialMedia),
    Facebook(StringRes.facebook, "ic_qr_facebook", true, GenerateHeader.SocialMedia),
    Twitter(StringRes.twitter, "ic_qr_twitter", true, GenerateHeader.SocialMedia),
    TikTok(StringRes.tiktok, "ic_qr_tiktok", true, GenerateHeader.SocialMedia),
    Telegram(StringRes.telegram, "ic_qr_telegram", true, GenerateHeader.SocialMedia),
    VKontakte(StringRes.vkontakte, "ic_qr_vkontakte", true, GenerateHeader.SocialMedia),
    Twitch(StringRes.twitch, "ic_qr_twitch", true, GenerateHeader.SocialMedia),
    LinkedIn(StringRes.linkedin, "ic_qr_linkedin", true, GenerateHeader.SocialMedia),
    Github(StringRes.github, "ic_qr_github", true, GenerateHeader.SocialMedia),
    Medium(StringRes.medium, "ic_qr_medium", true, GenerateHeader.SocialMedia),
    Dribbble(StringRes.dribbble, "ic_qr_dribbble", true, GenerateHeader.SocialMedia),
    Behance(StringRes.behance, "ic_qr_behance", true, GenerateHeader.SocialMedia),
}

enum class GenerateHeader {
    Web,
    Communication,
    SocialMedia,
    Other
}

fun Long.toGenerateMode(): GenerateMode {
    return GenerateMode.values()
        .firstOrNull { it.ordinal.toLong() == this } ?: GenerateMode.Text
}