package com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type

import com.qr.qrcode.barcode.scanner.reader.qreader.core.helpers.StringRes

enum class GenerateMode(
    val title: String,
    val icon: String,
    val header: GenerateHeader
) {
    Text(StringRes.text, "ic_qr_text", GenerateHeader.Web),
    Website(StringRes.website, "ic_qr_website", GenerateHeader.Web),
    Sms(StringRes.sms, "ic_qr_sms", GenerateHeader.Communication),
    PhoneNumber(StringRes.phoneNumber, "ic_qr_phone", GenerateHeader.Communication),
    EmailAddress(StringRes.emailAddress, "ic_qr_email", GenerateHeader.Communication),
    Wifi(StringRes.wifi, "ic_qr_wifi", GenerateHeader.Other),
    ContactVCard(StringRes.contactVCard, "ic_qr_contact", GenerateHeader.Other),
    CalendarEvent(StringRes.calendarEvent, "ic_qr_event", GenerateHeader.Other),
    BizCard(StringRes.bizCard, "ic_qr_biz", GenerateHeader.Other),
    BusinessVCard(StringRes.businessVCard, "ic_qr_business", GenerateHeader.Other),
    Location(StringRes.location, "ic_qr_location", GenerateHeader.Other),
    Youtube(StringRes.youtube, "ic_qr_youtube", GenerateHeader.SocialMedia),
    WhatsApp(StringRes.whatsApp, "ic_qr_whatsapp", GenerateHeader.SocialMedia),
    Instagram(StringRes.instagram, "ic_qr_instagram", GenerateHeader.SocialMedia),
    Facebook(StringRes.facebook, "ic_qr_facebook", GenerateHeader.SocialMedia),
    Twitter(StringRes.twitter, "ic_qr_twitter", GenerateHeader.SocialMedia),
    TikTok(StringRes.tiktok, "ic_qr_tiktok", GenerateHeader.SocialMedia),
    Telegram(StringRes.telegram, "ic_qr_telegram", GenerateHeader.SocialMedia),
    VKontakte(StringRes.vkontakte, "ic_qr_vkontakte", GenerateHeader.SocialMedia),
    Twitch(StringRes.twitch, "ic_qr_twitch", GenerateHeader.SocialMedia),
    LinkedIn(StringRes.linkedin, "ic_qr_linkedin", GenerateHeader.SocialMedia),
    Github(StringRes.github, "ic_qr_github", GenerateHeader.SocialMedia),
    Medium(StringRes.medium, "ic_qr_medium", GenerateHeader.SocialMedia),
    Dribbble(StringRes.dribbble, "ic_qr_dribbble", GenerateHeader.SocialMedia),
    Behance(StringRes.behance, "ic_qr_behance", GenerateHeader.SocialMedia),
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