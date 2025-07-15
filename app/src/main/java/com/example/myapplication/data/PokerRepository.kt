package com.example.myapplication.data

import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.LocalDateTime

class PokerRepository(
    private val sessionDao: PokerSessionDao,
    private val playerDao: PokerPlayerDao
) {
    // Sesiones
    fun getAllSessions(): Flow<List<PokerSession>> = sessionDao.getAllSessions()
    
    fun getSessionByDate(date: LocalDate): Flow<PokerSession?> = sessionDao.getSessionByDate(date)
    
    fun getSessionsByDateRange(startDate: LocalDate, endDate: LocalDate): Flow<List<PokerSession>> = 
        sessionDao.getSessionsByDateRange(startDate, endDate)

    suspend fun saveSession(
        date: LocalDate,
        totalPositive: Int,
        totalNegative: Int,
        netResult: Int,
        alarmEndTime: LocalDateTime? = null,
        notes: String? = null
    ): Long {
        val session = PokerSession(
            date = date,
            totalPositive = totalPositive,
            totalNegative = totalNegative,
            netResult = netResult,
            alarmEndTime = alarmEndTime,
            notes = notes
        )
        return sessionDao.insertSession(session)
    }

    suspend fun updateSession(session: PokerSession) = sessionDao.updateSession(session)
    
    suspend fun deleteSession(session: PokerSession) = sessionDao.deleteSession(session)

    // Jugadores
    fun getPlayersBySessionId(sessionId: Long): Flow<List<PokerPlayer>> = 
        playerDao.getPlayersBySessionId(sessionId)

    suspend fun savePlayers(sessionId: Long, players: List<com.example.myapplication.Player>) {
        val pokerPlayers = players.map { player ->
            val result = if (player.paidBoxes > 0) {
                player.finalChips
            } else {
                player.finalChips - (player.unpaidBoxes * 5000)
            }
            
            PokerPlayer(
                sessionId = sessionId,
                name = player.name,
                paidBoxes = player.paidBoxes,
                unpaidBoxes = player.unpaidBoxes,
                finalChips = player.finalChips,
                paymentMethod = player.paymentMethod.name,
                result = result
            )
        }
        playerDao.insertPlayers(pokerPlayers)
    }

    fun getAllPlayerNames(): Flow<List<String>> = playerDao.getAllPlayerNames()

    suspend fun deleteSessionWithPlayers(sessionId: Long) {
        playerDao.deletePlayersBySessionId(sessionId)
        sessionDao.deleteSessionById(sessionId)
    }
} 