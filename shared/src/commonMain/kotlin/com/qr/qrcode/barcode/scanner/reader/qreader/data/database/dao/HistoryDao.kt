package com.qr.qrcode.barcode.scanner.reader.qreader.data.database.dao

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.qr.qrcode.barcode.scanner.reader.qreader.core.datetime.currentTimestamp
import com.qr.qrcode.barcode.scanner.reader.qreader.data.database.entity.HistoryEntity
import com.qr.qrcode.barcode.scanner.reader.qreader.data.database.entity.toEntity
import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.GenerateMode
import com.qr.qrcode.barcode.scanner.reader.qreader.db.AppDatabase
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.customize.CustomizeState
import com.qr.qrcode.barcode.scanner.reader.qreader.shared.ioDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class HistoryDao(database: AppDatabase) {

    private val queries = database.historyDatabaseQueries

    fun getHistory(isScanned: Boolean, query: String): Flow<List<HistoryEntity>> {
        return queries.getHistory(if (isScanned) 0 else 1)
            .asFlow()
            .mapToList(ioDispatcher)
            .map { log ->
                log.map { it.toEntity() }
                    .filter {
                        it.encoded.contains(query, ignoreCase = true)
                                || it.decoded.contains(query, ignoreCase = true)
                    }
                    .sortedByDescending { it.timestamp }
            }
            .flowOn(ioDispatcher)
    }

    fun insert(
        id: String,
        isScanned: Boolean,
        generateMode: GenerateMode,
        encoded: String,
        decoded: String,
        customize: CustomizeState
    ) {
        queries.insert(
            id = id,
            timestamp = currentTimestamp(),
            history_type = if (isScanned) 0 else 1,
            generate_mode = generateMode.ordinal.toLong(),
            encode = encoded,
            decode = decoded,
            selected_pattern = customize.selectedPattern.ordinal.toLong(),
            selected_corner = customize.selectedCorner.ordinal.toLong(),
            selected_dot = customize.selectedDot.ordinal.toLong(),
            pattern_dot_hex = customize.patternDotHex,
            pattern_background_hex = customize.patternBackgroundHex,
            frame_hex = customize.frameHex,
            frame_dot_hex = customize.frameDotHex,
            selected_logo = customize.selectedLogo,
            own_logo_path = customize.ownLogoPath
        )
    }

    fun delete(id: String) {
        queries.delete(id)
    }

    fun clearAll(isScanned: Boolean) {
        queries.clearAll(if (isScanned) 0 else 1)
    }
}