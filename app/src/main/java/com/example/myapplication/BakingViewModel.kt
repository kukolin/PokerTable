package com.example.myapplication

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import com.example.myapplication.data.PokerRepository
import com.example.myapplication.data.PokerSession
import com.example.myapplication.data.PokerPlayer

data class Player(
    val name: String,
    val paidBoxes: Int = 0,
    val unpaidBoxes: Int = 0,
    val finalChips: Int = 0,
    val paymentMethod: PaymentMethod = PaymentMethod.CASH
) {
    val totalBoxes: Int get() = paidBoxes + unpaidBoxes
}

enum class PaymentMethod {
    CASH, TRANSFER
}

data class SessionSummary(
    val players: List<Player>,
    val totalPositive: Int,
    val totalNegative: Int,
    val netResult: Int,
    val date: LocalDate
)

data class PokerAlarm(
    val endTime: LocalDateTime,
    val isActive: Boolean = false,
    val soundEnabled: Boolean = true,
    val vibrationEnabled: Boolean = true
) {
    val timeRemaining: Duration get() = Duration.between(LocalDateTime.now(), endTime)
    val isWarningTime: Boolean get() = timeRemaining.toMinutes() in 1..10
    val isExpired: Boolean get() = timeRemaining.isNegative
}

class PokerViewModel(
    private val repository: PokerRepository? = null
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Initial)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _currentScreen: MutableStateFlow<ScreenState> = MutableStateFlow(ScreenState.Players)
    val currentScreen: StateFlow<ScreenState> = _currentScreen.asStateFlow()

    private val _players: MutableStateFlow<List<Player>> = MutableStateFlow(emptyList())
    val players: StateFlow<List<Player>> = _players.asStateFlow()

    private val _sessionDate: MutableStateFlow<LocalDate> = MutableStateFlow(LocalDate.now())
    val sessionDate: StateFlow<LocalDate> = _sessionDate.asStateFlow()

    private val _summary: MutableStateFlow<SessionSummary?> = MutableStateFlow(null)
    val summary: StateFlow<SessionSummary?> = _summary.asStateFlow()

    private val _isPaused: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isPaused: StateFlow<Boolean> = _isPaused.asStateFlow()

    private val _alarm: MutableStateFlow<PokerAlarm?> = MutableStateFlow(null)
    val alarm: StateFlow<PokerAlarm?> = _alarm.asStateFlow()

    private val _showAlarmDialog: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val showAlarmDialog: StateFlow<Boolean> = _showAlarmDialog.asStateFlow()

    private val _requestExactAlarmPermission: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val requestExactAlarmPermission: StateFlow<Boolean> = _requestExactAlarmPermission.asStateFlow()

    // Nuevos estados para el historial
    private val _savedSessions: MutableStateFlow<List<PokerSession>> = MutableStateFlow(emptyList())
    val savedSessions: StateFlow<List<PokerSession>> = _savedSessions.asStateFlow()

    private val _currentSessionPlayers: MutableStateFlow<List<PokerPlayer>> = MutableStateFlow(emptyList())
    val currentSessionPlayers: StateFlow<List<PokerPlayer>> = _currentSessionPlayers.asStateFlow()

    private val _selectedSession: MutableStateFlow<PokerSession?> = MutableStateFlow(null)
    val selectedSession: StateFlow<PokerSession?> = _selectedSession.asStateFlow()

    fun addPlayer(name: String) {
        if (name.isBlank()) {
            _uiState.value = UiState.Error("El nombre no puede estar vacío")
            return
        }
        
        val currentPlayers = _players.value.toMutableList()
        currentPlayers.add(Player(name))
        _players.value = currentPlayers
        _uiState.value = UiState.Success
    }

    fun removePlayer(index: Int) {
        val currentPlayers = _players.value.toMutableList()
        if (index in currentPlayers.indices) {
            currentPlayers.removeAt(index)
            _players.value = currentPlayers
        }
    }

    fun addPaidBoxToPlayer(playerIndex: Int) {
        val currentPlayers = _players.value.toMutableList()
        if (playerIndex in currentPlayers.indices) {
            val player = currentPlayers[playerIndex]
            currentPlayers[playerIndex] = player.copy(paidBoxes = player.paidBoxes + 1)
            _players.value = currentPlayers
        }
    }

    fun addUnpaidBoxToPlayer(playerIndex: Int) {
        val currentPlayers = _players.value.toMutableList()
        if (playerIndex in currentPlayers.indices) {
            val player = currentPlayers[playerIndex]
            currentPlayers[playerIndex] = player.copy(unpaidBoxes = player.unpaidBoxes + 1)
            _players.value = currentPlayers
        }
    }

    fun removePaidBoxFromPlayer(playerIndex: Int) {
        val currentPlayers = _players.value.toMutableList()
        if (playerIndex in currentPlayers.indices) {
            val player = currentPlayers[playerIndex]
            if (player.paidBoxes > 0) {
                currentPlayers[playerIndex] = player.copy(paidBoxes = player.paidBoxes - 1)
                _players.value = currentPlayers
            }
        }
    }

    fun removeUnpaidBoxFromPlayer(playerIndex: Int) {
        val currentPlayers = _players.value.toMutableList()
        if (playerIndex in currentPlayers.indices) {
            val player = currentPlayers[playerIndex]
            if (player.unpaidBoxes > 0) {
                currentPlayers[playerIndex] = player.copy(unpaidBoxes = player.unpaidBoxes - 1)
                _players.value = currentPlayers
            }
        }
    }

    fun addBoxToPlayer(playerIndex: Int, boxCount: Int) {
        val currentPlayers = _players.value.toMutableList()
        if (playerIndex in currentPlayers.indices) {
            val player = currentPlayers[playerIndex]
            currentPlayers[playerIndex] = player.copy(paidBoxes = player.paidBoxes + boxCount)
            _players.value = currentPlayers
        }
    }

    fun updatePlayerFinalChips(playerIndex: Int, finalChips: Int) {
        val currentPlayers = _players.value.toMutableList()
        if (playerIndex in currentPlayers.indices) {
            val player = currentPlayers[playerIndex]
            currentPlayers[playerIndex] = player.copy(finalChips = finalChips)
            _players.value = currentPlayers
        }
    }

    fun updatePlayerPaymentMethod(playerIndex: Int, paymentMethod: PaymentMethod) {
        val currentPlayers = _players.value.toMutableList()
        if (playerIndex in currentPlayers.indices) {
            val player = currentPlayers[playerIndex]
            currentPlayers[playerIndex] = player.copy(paymentMethod = paymentMethod)
            _players.value = currentPlayers
        }
    }

    fun setSessionDate(date: LocalDate) {
        _sessionDate.value = date
    }

    fun nextScreen() {
        when (_currentScreen.value) {
            ScreenState.Players -> {
                if (_players.value.isEmpty()) {
                    _uiState.value = UiState.Error("Debe haber al menos un jugador")
                    return
                }
                _currentScreen.value = ScreenState.Boxes
            }
            ScreenState.Boxes -> {
                val totalBoxes = _players.value.sumOf { it.totalBoxes }
                if (totalBoxes == 0) {
                    _uiState.value = UiState.Error("Debe haber al menos una caja comprada")
                    return
                }
                _currentScreen.value = ScreenState.FinalCount
            }
            ScreenState.FinalCount -> {
                _currentScreen.value = ScreenState.Summary
                calculateSummary()
            }
            ScreenState.Summary -> {
                // Stay on summary screen
            }
            ScreenState.History -> {
                // Stay on history screen
            }
            ScreenState.SessionDetails -> {
                // Stay on session details screen
            }
        }
        _uiState.value = UiState.Success
    }

    fun previousScreen() {
        when (_currentScreen.value) {
            ScreenState.Players -> {
                // Stay on players screen
            }
            ScreenState.Boxes -> {
                _currentScreen.value = ScreenState.Players
            }
            ScreenState.FinalCount -> {
                _currentScreen.value = ScreenState.Boxes
            }
            ScreenState.Summary -> {
                _currentScreen.value = ScreenState.FinalCount
            }
            ScreenState.History -> {
                _currentScreen.value = ScreenState.Players
            }
            ScreenState.SessionDetails -> {
                _currentScreen.value = ScreenState.History
            }
        }
        _uiState.value = UiState.Success
    }

    fun goToHistory() {
        _currentScreen.value = ScreenState.History
        _uiState.value = UiState.Success
    }

    fun goToSessionDetails(session: PokerSession) {
        _selectedSession.value = session
        _currentScreen.value = ScreenState.SessionDetails
        loadSessionByDate(session.date, null)
        _uiState.value = UiState.Success
    }

    fun goBackFromDetails() {
        _selectedSession.value = null
        _currentScreen.value = ScreenState.History
        _uiState.value = UiState.Success
    }

    private fun calculateSummary() {
        val players = _players.value
        val date = _sessionDate.value
        
        val playersWithResults = players.map { player ->
            val paidBoxesValue = player.paidBoxes * 5000
            val unpaidBoxesValue = player.unpaidBoxes * 5000
            val finalChips = player.finalChips
            
            // Nueva lógica: 
            // - Si tiene cajas pagadas: debe recibir todas sus fichas finales
            // - Si tiene cajas no pagadas: debe recibir (fichas finales - valor cajas no pagadas)
            val result = if (player.paidBoxes > 0) {
                // Tiene cajas pagadas: debe recibir todas sus fichas finales
                finalChips
            } else {
                // Solo tiene cajas no pagadas: debe recibir (fichas finales - valor cajas no pagadas)
                finalChips - unpaidBoxesValue
            }
            
            player.copy(finalChips = finalChips)
        }
        
        val totalPositive = playersWithResults.sumOf { player ->
            val paidBoxesValue = player.paidBoxes * 5000
            val unpaidBoxesValue = player.unpaidBoxes * 5000
            val finalChips = player.finalChips
            
            val result = if (player.paidBoxes > 0) {
                finalChips
            } else {
                finalChips - unpaidBoxesValue
            }
            
            if (result > 0) result else 0
        }
        
        val totalNegative = playersWithResults.sumOf { player ->
            val paidBoxesValue = player.paidBoxes * 5000
            val unpaidBoxesValue = player.unpaidBoxes * 5000
            val finalChips = player.finalChips
            
            val result = if (player.paidBoxes > 0) {
                finalChips
            } else {
                finalChips - unpaidBoxesValue
            }
            
            if (result < 0) -result else 0
        }
        
        val netResult = totalPositive - totalNegative
        
        _summary.value = SessionSummary(
            players = playersWithResults,
            totalPositive = totalPositive,
            totalNegative = totalNegative,
            netResult = netResult,
            date = date
        )
    }

    fun copySummaryToClipboard(context: Context) {
        val summary = _summary.value ?: return
        
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val dateStr = summary.date.format(formatter)
        
        val summaryText = buildString {
            appendLine("📊 RESUMEN POKER - $dateStr")
            appendLine("=".repeat(30))
            appendLine()
            
            summary.players.forEach { player ->
                val paidBoxesValue = player.paidBoxes * 5000
                val unpaidBoxesValue = player.unpaidBoxes * 5000
                val finalChips = player.finalChips
                
                val result = if (player.paidBoxes > 0) {
                    finalChips
                } else {
                    finalChips - unpaidBoxesValue
                }
                
                val resultStr = if (result >= 0) "+$result" else "$result"
                val paymentStr = when (player.paymentMethod) {
                    PaymentMethod.CASH -> "Efectivo"
                    PaymentMethod.TRANSFER -> "Transferencia"
                }
                
                appendLine("👤 ${player.name}")
                if (player.paidBoxes > 0) {
                    appendLine("   Cajas pagadas: ${player.paidBoxes} (${paidBoxesValue} fichas)")
                }
                if (player.unpaidBoxes > 0) {
                    appendLine("   Cajas sin pagar: ${player.unpaidBoxes} (${unpaidBoxesValue} fichas)")
                }
                appendLine("   Fichas finales: ${player.finalChips}")
                appendLine("   Resultado: $resultStr")
                appendLine("   Pago: $paymentStr")
                appendLine()
            }
            
            // Recalcular totales con la lógica correcta
            val recalculatedPositive = summary.players.sumOf { player ->
                val result = if (player.paidBoxes > 0) {
                    player.finalChips
                } else {
                    player.finalChips - (player.unpaidBoxes * 5000)
                }
                if (result > 0) result else 0
            }
            
            val recalculatedNegative = summary.players.sumOf { player ->
                val result = if (player.paidBoxes > 0) {
                    player.finalChips
                } else {
                    player.finalChips - (player.unpaidBoxes * 5000)
                }
                if (result < 0) -result else 0
            }
            
            val recalculatedNet = recalculatedPositive - recalculatedNegative
            
            appendLine("💰 TOTALES:")
            appendLine("   Positivo: +${recalculatedPositive}")
            appendLine("   Negativo: -${recalculatedNegative}")
            appendLine("   Neto: ${if (recalculatedNet >= 0) "+${recalculatedNet}" else "${recalculatedNet}"}")
        }
        
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
        val clip = android.content.ClipData.newPlainText("Resumen Poker", summaryText)
        clipboard.setPrimaryClip(clip)
        
        _uiState.value = UiState.Success
    }

    fun resetSession() {
        _players.value = emptyList()
        _currentScreen.value = ScreenState.Players
        _sessionDate.value = LocalDate.now()
        _summary.value = null
        _uiState.value = UiState.Success
    }

    fun togglePause() {
        _isPaused.value = !_isPaused.value
    }

    // Alarm Management Functions
    fun calculateEndTime(selectedTime: LocalTime): LocalDateTime {
        val currentTime = LocalTime.now()
        val today = LocalDate.now()
        val tomorrow = today.plusDays(1)
        
        return when {
            // Si la hora seleccionada es menor a la actual, es mañana
            selectedTime.isBefore(currentTime) -> LocalDateTime.of(tomorrow, selectedTime)
            // Si la diferencia es más de 5 horas, ajustar a máximo 5 horas
            Duration.between(currentTime, selectedTime).toHours() > 5 -> 
                LocalDateTime.of(today, currentTime.plusHours(5))
            // Caso normal
            else -> LocalDateTime.of(today, selectedTime)
        }
    }

    fun resetRequestExactAlarmPermission() {
        _requestExactAlarmPermission.value = false
    }

    fun setAlarm(endTime: LocalDateTime, context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            if (!alarmManager.canScheduleExactAlarms()) {
                _requestExactAlarmPermission.value = true
                return
            }
        }
        
        // Cancelar alarma anterior si existe
        cancelScheduledAlarm(context)
        
        val alarm = PokerAlarm(endTime = endTime, isActive = true)
        _alarm.value = alarm
        scheduleAlarm(context, alarm)
    }

    fun cancelAlarm(context: Context) {
        _alarm.value = null
        cancelScheduledAlarm(context)
    }

    fun showAlarmDialog() {
        _showAlarmDialog.value = true
    }

    fun hideAlarmDialog() {
        _showAlarmDialog.value = false
    }

    fun extendAlarm(minutes: Int, context: Context) {
        _alarm.value?.let { currentAlarm ->
            val newEndTime = currentAlarm.endTime.plusMinutes(minutes.toLong())
            val newAlarm = currentAlarm.copy(endTime = newEndTime)
            _alarm.value = newAlarm
            scheduleAlarm(context, newAlarm)
        }
    }

    private fun scheduleAlarm(context: Context, alarm: PokerAlarm) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, PokerAlarmReceiver::class.java).apply {
            action = "POKER_ALARM"
            putExtra("endTime", alarm.endTime.toString())
        }
        
        val pendingIntent = PendingIntent.getBroadcast(
            context, 
            0, 
            intent, 
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        // Programar alarma 10 minutos antes
        val warningTime = alarm.endTime.minusMinutes(10)
        val currentTime = LocalDateTime.now()
        
        if (warningTime.isAfter(currentTime)) {
            try {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    warningTime.toEpochSecond(java.time.ZoneOffset.UTC) * 1000,
                    pendingIntent
                )
            } catch (e: SecurityException) {
                // Manejar error de permisos
                _requestExactAlarmPermission.value = true
            }
        } else {
            // Si el tiempo de advertencia ya pasó, activar inmediatamente
            _alarm.value = alarm.copy(isActive = false)
            triggerAlarm(context)
        }
    }

    private fun cancelScheduledAlarm(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, PokerAlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context, 
            0, 
            intent, 
            PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
        )
        pendingIntent?.let { alarmManager.cancel(it) }
    }

    fun triggerAlarm(context: Context) {
        // Evitar activaciones múltiples
        if (_showAlarmDialog.value) return
        
        // Vibración
        try {
            val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (vibrator.hasVibrator()) {
                vibrator.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE))
            }
        } catch (e: Exception) {
            // Fallback si no hay vibración
        }
        
        showAlarmDialog()
    }

    // Funciones para guardar y cargar sesiones
    fun saveCurrentSession(context: Context) {
        val summary = _summary.value ?: return
        val currentAlarm = _alarm.value
        
        viewModelScope.launch {
            try {
                val sessionId = repository?.saveSession(
                    date = summary.date,
                    totalPositive = summary.totalPositive,
                    totalNegative = summary.totalNegative,
                    netResult = summary.netResult,
                    alarmEndTime = currentAlarm?.endTime
                )
                
                sessionId?.let { id ->
                    repository?.savePlayers(id, summary.players)
                }
                
                _uiState.value = UiState.Success
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Error al guardar la sesión: ${e.message}")
            }
        }
    }

    fun loadAllSessions(context: Context) {
        viewModelScope.launch {
            try {
                repository?.getAllSessions()?.collect { sessions ->
                    _savedSessions.value = sessions
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Error al cargar sesiones: ${e.message}")
            }
        }
    }

    fun loadSessionByDate(date: LocalDate, context: Context? = null) {
        viewModelScope.launch {
            try {
                repository?.getSessionByDate(date)?.collect { session ->
                    session?.let { pokerSession ->
                        repository.getPlayersBySessionId(pokerSession.id).collect { players ->
                            _currentSessionPlayers.value = players
                        }
                    }
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Error al cargar sesión: ${e.message}")
            }
        }
    }

    fun deleteSession(session: PokerSession, context: Context) {
        viewModelScope.launch {
            try {
                repository?.deleteSessionWithPlayers(session.id)
                loadAllSessions(context)
                _uiState.value = UiState.Success
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Error al eliminar sesión: ${e.message}")
            }
        }
    }
}