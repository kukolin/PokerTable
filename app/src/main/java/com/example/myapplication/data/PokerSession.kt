package com.example.myapplication.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalDateTime

@Entity(tableName = "poker_sessions")
data class PokerSession(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val date: LocalDate,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val totalPositive: Int,
    val totalNegative: Int,
    val netResult: Int,
    val alarmEndTime: LocalDateTime? = null,
    val notes: String? = null
)

@Entity(tableName = "poker_players")
data class PokerPlayer(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val sessionId: Long,
    val name: String,
    val paidBoxes: Int,
    val unpaidBoxes: Int,
    val finalChips: Int,
    val paymentMethod: String, // "CASH" o "TRANSFER"
    val result: Int // Resultado final del jugador
) 