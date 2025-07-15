package com.example.myapplication.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface PokerSessionDao {
    @Query("SELECT * FROM poker_sessions ORDER BY date DESC")
    fun getAllSessions(): Flow<List<PokerSession>>

    @Query("SELECT * FROM poker_sessions WHERE date = :date")
    fun getSessionByDate(date: LocalDate): Flow<PokerSession?>

    @Query("SELECT * FROM poker_sessions WHERE date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    fun getSessionsByDateRange(startDate: LocalDate, endDate: LocalDate): Flow<List<PokerSession>>

    @Insert
    suspend fun insertSession(session: PokerSession): Long

    @Update
    suspend fun updateSession(session: PokerSession)

    @Delete
    suspend fun deleteSession(session: PokerSession)

    @Query("DELETE FROM poker_sessions WHERE id = :sessionId")
    suspend fun deleteSessionById(sessionId: Long)
}

@Dao
interface PokerPlayerDao {
    @Query("SELECT * FROM poker_players WHERE sessionId = :sessionId")
    fun getPlayersBySessionId(sessionId: Long): Flow<List<PokerPlayer>>

    @Insert
    suspend fun insertPlayer(player: PokerPlayer)

    @Insert
    suspend fun insertPlayers(players: List<PokerPlayer>)

    @Update
    suspend fun updatePlayer(player: PokerPlayer)

    @Delete
    suspend fun deletePlayer(player: PokerPlayer)

    @Query("DELETE FROM poker_players WHERE sessionId = :sessionId")
    suspend fun deletePlayersBySessionId(sessionId: Long)

    @Query("SELECT DISTINCT name FROM poker_players ORDER BY name")
    fun getAllPlayerNames(): Flow<List<String>>
} 