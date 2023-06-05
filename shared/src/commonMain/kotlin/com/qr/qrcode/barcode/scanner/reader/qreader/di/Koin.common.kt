package com.qr.qrcode.barcode.scanner.reader.qreader.di

import com.qr.qrcode.barcode.scanner.reader.qreader.data.datastore.AppStore
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.unloadKoinModules
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

expect fun platformModule(): Module

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(
            storeModule(),
            platformModule()
        )
    }

fun storeModule() = module {
    singleOf(::AppStore)
}

fun reloadKoinModules() {
    unloadKoinModules(
        listOf(
            storeModule(),
            platformModule()
        )
    )
    loadKoinModules(
        listOf(
            storeModule(),
            platformModule()
        )
    )
}