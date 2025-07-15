package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.lifecycle.viewmodel.compose.viewModel
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import com.example.myapplication.ui.theme.PokerCard
import com.example.myapplication.ui.theme.PokerDark
import com.example.myapplication.ui.theme.PokerGold
import com.example.myapplication.ui.theme.PokerGoldDark
import com.example.myapplication.ui.theme.PokerWhite
import com.example.myapplication.ui.theme.PokerWhiteLight
import com.example.myapplication.ui.theme.PokerGray
import com.example.myapplication.ui.theme.PokerRed
import com.example.myapplication.ui.theme.PokerBorder
import com.example.myapplication.ui.theme.PokerGrayLight
import com.example.myapplication.ui.theme.PokerRed
import com.example.myapplication.ui.theme.PokerBlack
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.foundation.border
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.material3.TimeInput
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import com.example.myapplication.data.PokerRepository
import com.example.myapplication.data.PokerSession
import com.example.myapplication.data.PokerPlayer

@Composable
fun AnimatedPokerBackground() {
    val infiniteTransition = rememberInfiniteTransition()
    
    // Elegant Wild Color Palette
    val deepPurple = Color(0xFF2D1B69)
    val royalBlue = Color(0xFF1E3A8A)
    val elegantGold = Color(0xFFD4AF37)
    val emeraldGreen = Color(0xFF059669)
    val crimsonRed = Color(0xFFDC2626)
    val darkSpace = Color(0xFF0A0A0A)
    val cosmicPink = Color(0xFFBE185D)
    
    // Slower, more elegant animations
    val cosmicPulse by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.8f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    
    val nebulaShift by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(8000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    
    val starField by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 0.9f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    
    val cosmicRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(5000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )
    
    val energyFlow by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(6000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    
    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
    ) {
        val width = constraints.maxWidth.toFloat()
        val height = constraints.maxHeight.toFloat()
        
        // Deep space background
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.linearGradient(
                        colors = listOf(
                            darkSpace,
                            deepPurple.copy(alpha = 0.3f),
                            royalBlue.copy(alpha = 0.2f),
                            darkSpace
                        ),
                        start = androidx.compose.ui.geometry.Offset(0f, 0f),
                        end = androidx.compose.ui.geometry.Offset(width, height)
                    )
                )
        )
        
        // Cosmic Nebula
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            cosmicPink.copy(alpha = 0.1f + nebulaShift * 0.1f),
                            emeraldGreen.copy(alpha = 0.05f + nebulaShift * 0.05f),
                            Color.Transparent
                        ),
                        center = androidx.compose.ui.geometry.Offset(width * 0.7f, height * 0.3f),
                        radius = width * (0.4f + nebulaShift * 0.2f)
                    )
                )
        )
        
        // Elegant Card Symbols Rotation
        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(
                    rotationZ = cosmicRotation,
                    transformOrigin = androidx.compose.ui.graphics.TransformOrigin.Center
                )
        ) {
            // Picas (Spades) - Royal Blue - 0° (arriba)
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .offset(
                        x = (width * 0.5f - 30f).dp,
                        y = (height * 0.5f - 30f - 100f).dp
                    )
                    .background(
                        color = Color.Transparent,
                        shape = androidx.compose.foundation.shape.CircleShape
                    )
                    .border(
                        width = (2 + cosmicPulse * 3).dp,
                        color = royalBlue.copy(alpha = cosmicPulse * 0.8f),
                        shape = androidx.compose.foundation.shape.CircleShape
                    ),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                Text(
                    text = "♠",
                    fontSize = 30.sp,
                    color = royalBlue.copy(alpha = cosmicPulse * 0.9f),
                    fontWeight = FontWeight.Bold
                )
            }
            
            // Corazones (Hearts) - Cosmic Pink - 90° (derecha)
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .offset(
                        x = (width * 0.5f - 30f + 100f).dp,
                        y = (height * 0.5f - 30f).dp
                    )
                    .background(
                        color = Color.Transparent,
                        shape = androidx.compose.foundation.shape.CircleShape
                    )
                    .border(
                        width = (2 + cosmicPulse * 3).dp,
                        color = cosmicPink.copy(alpha = cosmicPulse * 0.8f),
                        shape = androidx.compose.foundation.shape.CircleShape
                    ),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                Text(
                    text = "♥",
                    fontSize = 30.sp,
                    color = cosmicPink.copy(alpha = cosmicPulse * 0.9f),
                    fontWeight = FontWeight.Bold
                )
            }
            
            // Diamantes (Diamonds) - Elegant Gold - 180° (abajo)
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .offset(
                        x = (width * 0.5f - 30f).dp,
                        y = (height * 0.5f - 30f + 100f).dp
                    )
                    .background(
                        color = Color.Transparent,
                        shape = androidx.compose.foundation.shape.CircleShape
                    )
                    .border(
                        width = (2 + cosmicPulse * 3).dp,
                        color = elegantGold.copy(alpha = cosmicPulse * 0.8f),
                        shape = androidx.compose.foundation.shape.CircleShape
                    ),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                Text(
                    text = "♦",
                    fontSize = 30.sp,
                    color = elegantGold.copy(alpha = cosmicPulse * 0.9f),
                    fontWeight = FontWeight.Bold
                )
            }
            
            // Tréboles (Clubs) - Emerald Green - 270° (izquierda)
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .offset(
                        x = (width * 0.5f - 30f - 100f).dp,
                        y = (height * 0.5f - 30f).dp
                    )
                    .background(
                        color = Color.Transparent,
                        shape = androidx.compose.foundation.shape.CircleShape
                    )
                    .border(
                        width = (2 + cosmicPulse * 3).dp,
                        color = emeraldGreen.copy(alpha = cosmicPulse * 0.8f),
                        shape = androidx.compose.foundation.shape.CircleShape
                    ),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                Text(
                    text = "♣",
                    fontSize = 30.sp,
                    color = emeraldGreen.copy(alpha = cosmicPulse * 0.9f),
                    fontWeight = FontWeight.Bold
                )
            }
        }
        
        // Elegant Star Field
        val starPositions = listOf(
            Pair(0.1f, 0.15f), Pair(0.25f, 0.08f), Pair(0.4f, 0.22f),
            Pair(0.15f, 0.45f), Pair(0.55f, 0.38f), Pair(0.75f, 0.12f),
            Pair(0.08f, 0.35f), Pair(0.65f, 0.55f), Pair(0.35f, 0.65f),
            Pair(0.85f, 0.25f), Pair(0.45f, 0.75f), Pair(0.22f, 0.85f)
        )
        
        starPositions.forEach { (x, y) ->
            Box(
                modifier = Modifier
                    .offset(
                        x = (width * x).dp,
                        y = (height * y).dp
                    )
                    .size((2 + starField * 3).dp)
                    .background(
                        color = elegantGold.copy(alpha = starField * 0.8f),
                        shape = androidx.compose.foundation.shape.CircleShape
                    )
            )
        }
        
        // Energy Flow
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.linearGradient(
                        colors = listOf(
                            Color.Transparent,
                            elegantGold.copy(alpha = energyFlow * 0.3f),
                            cosmicPink.copy(alpha = energyFlow * 0.2f),
                            Color.Transparent
                        ),
                        start = androidx.compose.ui.geometry.Offset(0f, 0f),
                        end = androidx.compose.ui.geometry.Offset(width, height)
                    )
                )
        )
        
        // Cosmic Center
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            elegantGold.copy(alpha = cosmicPulse * 0.2f),
                            cosmicPink.copy(alpha = cosmicPulse * 0.1f),
                            Color.Transparent
                        ),
                        center = androidx.compose.ui.geometry.Offset(width * 0.5f, height * 0.5f),
                        radius = width * (0.08f + cosmicPulse * 0.15f)
                    )
                )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun PokerScreen(
    repository: PokerRepository? = null
) {
    val pokerViewModel: PokerViewModel = viewModel { PokerViewModel(repository) }
    val uiState by pokerViewModel.uiState.collectAsState()
    val currentScreen by pokerViewModel.currentScreen.collectAsState()
    val players by pokerViewModel.players.collectAsState()
    val sessionDate by pokerViewModel.sessionDate.collectAsState()
    val summary by pokerViewModel.summary.collectAsState()
    val alarm by pokerViewModel.alarm.collectAsState()
    val requestExactAlarmPermission by pokerViewModel.requestExactAlarmPermission.collectAsState()
    val showAlarmDialog by pokerViewModel.showAlarmDialog.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(uiState) {
        if (uiState is UiState.Error) {
            // Show error message (you could add a snackbar here)
        }
    }
    
    // Handle alarm when app is in background
    LaunchedEffect(alarm?.isWarningTime) {
        if (alarm?.isWarningTime == true && !showAlarmDialog) {
            // Trigger alarm even if app is in background
            pokerViewModel.triggerAlarm(context)
        }
    }

    // Timer to update alarm status - CORREGIDO
    LaunchedEffect(alarm?.isActive) {
        if (alarm?.isActive == true) {
            while (true) {
                delay(1000) // Update every second
                // Solo verificar si la alarma debe activarse si no está ya activa
                if (alarm?.isWarningTime == true && !showAlarmDialog) {
                    pokerViewModel.triggerAlarm(context)
                }
                // Salir del bucle si la alarma ya no está activa
                if (alarm?.isActive != true) break
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Animated poker background
        AnimatedPokerBackground()

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Animated Header
            AnimatedVisibility(
                visible = true,
                enter = slideInVertically(
                    initialOffsetY = { -it },
                    animationSpec = tween(1000)
                ) + fadeIn(animationSpec = tween(1000))
            ) {
                TopAppBar(
                    title = { 
                        Text(
                            stringResource(R.string.poker_title),
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontSize = 24.sp
                            ),
                            fontWeight = FontWeight.Bold,
                            color = PokerWhite
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent
                    ),
                    actions = {
                        // Botón de historial
                        IconButton(
                            onClick = { pokerViewModel.goToHistory() },
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.8f)
                            )
                        ) {
                            Icon(Icons.Filled.History, contentDescription = "Historial")
                        }
                        
                        // Alarm indicator
                        if (alarm?.isActive == true) {
                            Card(
                                colors = CardDefaults.cardColors(
                                    containerColor = if (alarm?.isWarningTime == true) PokerRed else PokerGold
                                ),
                                modifier = Modifier.padding(end = 8.dp)
                            ) {
                                Text(
                                    text = alarm?.timeRemaining?.let { 
                                        val hours = it.toHours()
                                        val minutes = it.toMinutes() % 60
                                        "${hours}:${String.format("%02d", minutes)}"
                                    } ?: "",
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.Bold,
                                    color = if (alarm?.isWarningTime == true) PokerWhite else PokerBlack,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }
                        
                        if (currentScreen == ScreenState.Summary) {
                            TextButton(
                                onClick = { pokerViewModel.copySummaryToClipboard(context) },
                                colors = ButtonDefaults.textButtonColors(
                                    containerColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.8f)
                                )
                            ) {
                                Text(stringResource(R.string.action_copy))
                            }
                            IconButton(
                                onClick = { pokerViewModel.resetSession() },
                                colors = IconButtonDefaults.iconButtonColors(
                                    containerColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.8f)
                                )
                            ) {
                                Icon(Icons.Filled.Refresh, contentDescription = "Nueva sesión")
                            }
                        }
                    }
                )
            }

            // Animated Content
            AnimatedContent(
                targetState = currentScreen,
                transitionSpec = {
                    val direction = if (targetState.ordinal > initialState.ordinal) 1 else -1
                    slideInHorizontally(
                        initialOffsetX = { direction * it },
                        animationSpec = tween(500)
                    ) + fadeIn(animationSpec = tween(500)) with slideOutHorizontally(
                        targetOffsetX = { -direction * it },
                        animationSpec = tween(500)
                    ) + fadeOut(animationSpec = tween(500))
                }
            ) { screen ->
                when (screen) {
                    ScreenState.Players -> PlayersScreen(pokerViewModel, players, sessionDate)
                    ScreenState.Boxes -> BoxesScreen(pokerViewModel, players)
                    ScreenState.FinalCount -> FinalCountScreen(pokerViewModel, players)
                    ScreenState.Summary -> SummaryScreen(pokerViewModel, summary)
                    ScreenState.History -> HistoryScreen(pokerViewModel)
                    ScreenState.SessionDetails -> {
                        val selectedSession by pokerViewModel.selectedSession.collectAsState()
                        selectedSession?.let { session ->
                            SessionDetailsScreen(pokerViewModel, session)
                        } ?: HistoryScreen(pokerViewModel)
                    }
                }
            }
        }
    }

    // Dialog to request exact alarm permission
    if (requestExactAlarmPermission) {
        AlertDialog(
            onDismissRequest = { pokerViewModel.resetRequestExactAlarmPermission() },
            title = {
                Text("Permiso necesario", fontWeight = FontWeight.Bold, color = PokerRed)
            },
            text = {
                Text("Para programar la alarma, debes habilitar 'Permitir alarmas exactas' en la configuración del sistema.", color = PokerWhite)
            },
            confirmButton = {
                Button(onClick = {
                    pokerViewModel.resetRequestExactAlarmPermission()
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        context.startActivity(intent)
                    }
                }, colors = ButtonDefaults.buttonColors(containerColor = PokerGold)) {
                    Text("Abrir configuración")
                }
            },
            dismissButton = {
                TextButton(onClick = { pokerViewModel.resetRequestExactAlarmPermission() }) {
                    Text("Cancelar", color = PokerWhite)
                }
            },
            containerColor = PokerCard,
            titleContentColor = PokerWhite,
            textContentColor = PokerWhite
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayersScreen(
    pokerViewModel: PokerViewModel,
    players: List<Player>,
    sessionDate: LocalDate
) {
    var newPlayerName by remember { mutableStateOf("") }
    var showDatePicker by remember { mutableStateOf(false) }
    var showAlarmPicker by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    
    val alarm by pokerViewModel.alarm.collectAsState()
    val showAlarmDialog by pokerViewModel.showAlarmDialog.collectAsState()
    val context = LocalContext.current
    
    // Animation for cards
    val infiniteTransition = rememberInfiniteTransition()
    val cardScale by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    
    // Main scrollable content
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        contentPadding = PaddingValues(bottom = 40.dp)
    ) {
        // Convocatoria Pokeriana section
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = PokerCard.copy(alpha = 0.95f)
                )
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "Convocatoria Pokeriana",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = PokerWhite
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = { showDatePicker = true },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PokerGold
                        )
                    ) {
                        Text(
                            sessionDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                            fontWeight = FontWeight.Bold,
                            color = PokerBlack
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
        // Alarm configuration section
        item {
            AnimatedVisibility(
                visible = true,
                enter = slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = tween(800, delayMillis = 100)
                ) + fadeIn(animationSpec = tween(800, delayMillis = 100))
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = PokerCard.copy(alpha = 0.95f)
                    )
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "⏰ ALARMA DE FINALIZACIÓN",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = PokerWhite
                            )
                            // Alarm status indicator
                            Card(
                                colors = CardDefaults.cardColors(
                                    containerColor = if (alarm?.isActive == true) PokerGold else PokerGrayLight
                                ),
                                modifier = Modifier.padding(4.dp)
                            ) {
                                Text(
                                    text = if (alarm?.isActive == true) "ACTIVA" else "INACTIVA",
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.Bold,
                                    color = if (alarm?.isActive == true) PokerBlack else PokerWhiteLight,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        if (alarm?.isActive == true) {
                            // Show alarm info
                            Column {
                                Text(
                                    text = "Termina en: ${alarm?.endTime?.format(DateTimeFormatter.ofPattern("HH:mm"))}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = PokerGold,
                                    fontWeight = FontWeight.Bold
                                )
                                val timeRemaining = alarm?.timeRemaining
                                if (timeRemaining != null) {
                                    val hours = timeRemaining.toHours()
                                    val minutes = timeRemaining.toMinutes() % 60
                                    Text(
                                        text = "Tiempo restante: ${hours}h ${minutes}min",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = if (alarm?.isWarningTime == true) PokerRed else PokerWhiteLight
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    OutlinedButton(
                                        onClick = { showAlarmPicker = true },
                                        modifier = Modifier.weight(1f),
                                        colors = ButtonDefaults.outlinedButtonColors(
                                            contentColor = PokerWhite
                                        )
                                    ) {
                                        Text("Cambiar Hora")
                                    }
                                    Button(
                                        onClick = { pokerViewModel.cancelAlarm(context) },
                                        modifier = Modifier.weight(1f),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = PokerRed
                                        )
                                    ) {
                                        Text("Cancelar")
                                    }
                                }
                            }
                        } else {
                            // Show quick time buttons
                            Column {
                                Text(
                                    text = "Horarios sugeridos:",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = PokerWhiteLight
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                // Grid de horarios sugeridos
                                LazyVerticalGrid(
                                    columns = GridCells.Fixed(3),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp),
                                    modifier = Modifier.heightIn(max = 160.dp)
                                ) {
                                    val suggestedTimes = listOf("22:00", "23:30", "00:30", "01:00", "02:00", "03:00")
                                    items(suggestedTimes) { timeString ->
                                        Button(
                                            onClick = {
                                                val time = LocalTime.parse(timeString)
                                                val endTime = pokerViewModel.calculateEndTime(time)
                                                pokerViewModel.setAlarm(endTime, context)
                                                coroutineScope.launch {
                                                    snackbarHostState.showSnackbar(
                                                        message = "Alarma configurada para ${endTime.format(DateTimeFormatter.ofPattern("HH:mm"))}",
                                                        duration = androidx.compose.material3.SnackbarDuration.Short
                                                    )
                                                }
                                            },
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = PokerGold
                                            ),
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            Text(
                                                timeString,
                                                fontSize = 12.sp,
                                                color = PokerBlack,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                OutlinedButton(
                                    onClick = { showAlarmPicker = true },
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = ButtonDefaults.outlinedButtonColors(
                                        contentColor = PokerWhite
                                    )
                                ) {
                                    Text("Hora Personalizada")
                                }
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
        // Add player section with animation
        item {
            AnimatedVisibility(
                visible = true,
                enter = slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = tween(800, delayMillis = 200)
                ) + fadeIn(animationSpec = tween(800, delayMillis = 200))
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = PokerCard.copy(alpha = 0.95f)
                    )
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text(
                            text = stringResource(R.string.add_player),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = PokerWhite
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        TextField(
                            value = newPlayerName,
                            onValueChange = { 
                                // Capitalize first letter
                                if (it.isNotEmpty()) {
                                    newPlayerName = it.replaceFirstChar { it.uppercase() }
                                } else {
                                    newPlayerName = it
                                }
                            },
                            label = { Text(stringResource(R.string.player_name)) },
                            placeholder = { 
                                Text(
                                    if (players.isEmpty()) "Primer jugador..." 
                                    else "Ej: ${if (players.size % 2 == 0) "Nacho" else "María"}"
                                ) 
                            },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                                capitalization = KeyboardCapitalization.Words,
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = androidx.compose.foundation.text.KeyboardActions(
                                onDone = {
                                    if (newPlayerName.isNotBlank()) {
                                        pokerViewModel.addPlayer(newPlayerName)
                                        newPlayerName = ""
                                        focusManager.clearFocus()
                                    }
                                }
                            ),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = PokerGrayLight.copy(alpha = 0.9f),
                                unfocusedContainerColor = PokerGrayLight.copy(alpha = 0.7f),
                                focusedLabelColor = PokerWhite,
                                unfocusedLabelColor = PokerWhiteLight,
                                focusedTextColor = PokerWhite,
                                unfocusedTextColor = PokerWhite
                            )
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = {
                                    pokerViewModel.addPlayer(newPlayerName)
                                    newPlayerName = ""
                                    coroutineScope.launch {
                                        snackbarHostState.showSnackbar(
                                            message = "Jugador agregado exitosamente",
                                            duration = androidx.compose.material3.SnackbarDuration.Short
                                        )
                                    }
                                },
                                enabled = newPlayerName.isNotBlank(),
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (newPlayerName.isNotBlank()) PokerGold else PokerGrayLight,
                                    disabledContainerColor = PokerGrayLight
                                )
                            ) {
                                Icon(Icons.Filled.Add, contentDescription = "Agregar jugador")
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    stringResource(R.string.action_add),
                                    fontWeight = FontWeight.Bold,
                                    color = if (newPlayerName.isNotBlank()) PokerBlack else PokerWhiteLight
                                )
                            }
                            if (newPlayerName.isBlank()) {
                                OutlinedButton(
                                    onClick = {
                                        pokerViewModel.addPlayer("Jugador ${players.size + 1}")
                                        coroutineScope.launch {
                                            snackbarHostState.showSnackbar(
                                                message = "Jugador agregado automáticamente",
                                                duration = androidx.compose.material3.SnackbarDuration.Short
                                            )
                                        }
                                    },
                                    modifier = Modifier.weight(0.5f)
                                ) {
                                    Text("Rápido", fontSize = 12.sp)
                                }
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
        // Players list header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.players_title),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = PokerWhite
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (players.size > 8) {
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = PokerRed.copy(alpha = 0.8f)
                            ),
                            modifier = Modifier.padding(4.dp)
                        ) {
                            Text(
                                text = "📜 Scroll",
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Bold,
                                color = PokerWhite,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = PokerGold.copy(alpha = 0.8f)
                        ),
                        modifier = Modifier.padding(4.dp)
                    ) {
                        Text(
                            text = "${players.size} jugadores",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold,
                            color = PokerBlack,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
        }
        // Players list
        itemsIndexed(players) { index, player ->
            AnimatedVisibility(
                visible = true,
                enter = slideInHorizontally(
                    initialOffsetX = { it },
                    animationSpec = tween(600, delayMillis = index * 100)
                ) + fadeIn(animationSpec = tween(600, delayMillis = index * 100))
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onLongPress = {
                                    pokerViewModel.removePlayer(index)
                                    coroutineScope.launch {
                                        snackbarHostState.showSnackbar(
                                            message = "Jugador '${player.name}' eliminado",
                                            duration = androidx.compose.material3.SnackbarDuration.Short
                                        )
                                    }
                                }
                            )
                        },
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = PokerGray.copy(alpha = 0.9f)
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = player.name,
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = PokerWhite
                                )
                                if (players.size > 10) {
                                    Card(
                                        colors = CardDefaults.cardColors(
                                            containerColor = PokerGold.copy(alpha = 0.6f)
                                        ),
                                        modifier = Modifier.size(24.dp)
                                    ) {
                                        Text(
                                            text = "${index + 1}",
                                            style = MaterialTheme.typography.bodySmall,
                                            fontWeight = FontWeight.Bold,
                                            color = PokerBlack,
                                            modifier = Modifier.padding(4.dp),
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }
                            }
                            if (player.totalBoxes > 0) {
                                Text(
                                    text = "${player.totalBoxes} cajas",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = PokerGold,
                                    fontSize = 12.sp
                                )
                            }
                        }
                        Row {
                            IconButton(
                                onClick = {
                                    pokerViewModel.removePlayer(index)
                                    coroutineScope.launch {
                                        snackbarHostState.showSnackbar(
                                            message = "Jugador '${player.name}' eliminado",
                                            duration = androidx.compose.material3.SnackbarDuration.Short
                                        )
                                    }
                                },
                                colors = IconButtonDefaults.iconButtonColors(
                                    containerColor = PokerRed.copy(alpha = 0.8f)
                                )
                            ) {
                                Icon(
                                    Icons.Filled.Delete,
                                    contentDescription = "Eliminar jugador ${player.name}",
                                    tint = PokerWhite,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
        // Navigation buttons with glow effect
        item {
            Spacer(modifier = Modifier.height(24.dp))
            AnimatedVisibility(
                visible = true,
                enter = slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = tween(800, delayMillis = 400)
                ) + fadeIn(animationSpec = tween(800, delayMillis = 400))
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = { pokerViewModel.nextScreen() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PokerGold
                        ),
                        modifier = Modifier.scale(cardScale)
                    ) {
                        Text(
                            "Siguiente", 
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = PokerBlack
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            Icons.Filled.ArrowForward, 
                            contentDescription = null,
                            tint = PokerBlack,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
        // Snackbar for feedback
        item {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
    
    // Alarm Dialog
    if (showAlarmDialog) {
        AlertDialog(
            onDismissRequest = { pokerViewModel.hideAlarmDialog() },
            title = {
                Text(
                    "🔔 ¡ATENCIÓN! ⏰",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = PokerRed
                )
            },
            text = {
                Column {
                    Text(
                        "La sesión termina en",
                        style = MaterialTheme.typography.bodyLarge,
                        color = PokerWhite
                    )
                    Text(
                        "10 minutos",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = PokerGold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "¿Qué deseas hacer?",
                        style = MaterialTheme.typography.bodyMedium,
                        color = PokerWhiteLight
                    )
                }
            },
            confirmButton = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = {
                            pokerViewModel.hideAlarmDialog()
                            pokerViewModel.nextScreen()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PokerRed
                        ),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Finalizar Ahora")
                    }
                    
                    Button(
                        onClick = {
                            pokerViewModel.extendAlarm(10, context)
                            pokerViewModel.hideAlarmDialog()
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "Sesión extendida 10 minutos",
                                    duration = androidx.compose.material3.SnackbarDuration.Short
                                )
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PokerGold
                        ),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("+10 min")
                    }
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { pokerViewModel.hideAlarmDialog() }
                ) {
                    Text("Continuar", color = PokerWhite)
                }
            },
            containerColor = PokerCard,
            titleContentColor = PokerWhite,
            textContentColor = PokerWhite
        )
    }
    
    // Time Picker Dialog
    if (showAlarmPicker) {
        val timePickerState = rememberTimePickerState(
            initialHour = LocalTime.now().hour,
            initialMinute = LocalTime.now().minute
        )
        
        AlertDialog(
            onDismissRequest = { showAlarmPicker = false },
            title = {
                Text(
                    "⏰ Configurar Hora de Finalización",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = PokerWhite
                )
            },
            text = {
                Column {
                    Text(
                        "Selecciona la hora de finalización:",
                        style = MaterialTheme.typography.bodyMedium,
                        color = PokerWhiteLight
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    TimePicker(
                        state = timePickerState,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val selectedTime = LocalTime.of(timePickerState.hour, timePickerState.minute)
                        val endTime = pokerViewModel.calculateEndTime(selectedTime)
                        pokerViewModel.setAlarm(endTime, context)
                        showAlarmPicker = false
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Alarma configurada para ${endTime.format(DateTimeFormatter.ofPattern("HH:mm"))}",
                                duration = androidx.compose.material3.SnackbarDuration.Short
                            )
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PokerGold
                    )
                ) {
                    Text("Configurar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showAlarmPicker = false }
                ) {
                    Text("Cancelar", color = PokerWhite)
                }
            },
            containerColor = PokerCard,
            titleContentColor = PokerWhite,
            textContentColor = PokerWhite
        )
    }
    
    if (showDatePicker) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = sessionDate.toEpochDay() * 24 * 60 * 60 * 1000
        )
        
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            val selectedDate = LocalDate.ofEpochDay(millis / (24 * 60 * 60 * 1000))
                            pokerViewModel.setSessionDate(selectedDate)
                        }
                        showDatePicker = false
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancelar")
                }
            }
        ) {
            DatePicker(
                state = datePickerState,
                showModeToggle = false
            )
        }
    }
}

@Composable
fun BoxesScreen(
    pokerViewModel: PokerViewModel,
    players: List<Player>
) {
    val isPaused by pokerViewModel.isPaused.collectAsState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        // Pause button at the top
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.boxes_title),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = PokerWhite
            )
            
            Button(
                onClick = { pokerViewModel.togglePause() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isPaused) PokerRed else PokerGold
                )
            ) {
                Text(
                    if (isPaused) "🔒 PAUSADO" else "⏸️ PAUSAR",
                    color = if (isPaused) PokerWhite else PokerBlack,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        
        Spacer(modifier = Modifier.height(12.dp))
        
        Text(
            text = stringResource(R.string.box_value),
            style = MaterialTheme.typography.bodyMedium,
            color = PokerWhiteLight
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Box(
            modifier = Modifier.weight(1f)
        ) {
            LazyColumn {
                itemsIndexed(players) { index, player ->
                    PlayerBoxCard(
                        player = player,
                        onAddPaidBox = {
                            if (!isPaused) pokerViewModel.addPaidBoxToPlayer(index)
                        },
                        onAddUnpaidBox = {
                            if (!isPaused) pokerViewModel.addUnpaidBoxToPlayer(index)
                        },
                        onRemovePaidBox = {
                            if (!isPaused) pokerViewModel.removePaidBoxFromPlayer(index)
                        },
                        onRemoveUnpaidBox = {
                            if (!isPaused) pokerViewModel.removeUnpaidBoxFromPlayer(index)
                        }
                    )
                }
            }
            
            // Pause overlay
            if (isPaused) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.7f))
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Card(
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "🔒 PAUSADO",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.error
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "La pantalla está bloqueada por seguridad",
                                style = MaterialTheme.typography.bodyMedium,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                onClick = { pokerViewModel.togglePause() },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.error
                                )
                            ) {
                                Text("DESBLOQUEAR")
                            }
                        }
                    }
                }
            }
        }
        
        // Navigation buttons
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedButton(
                onClick = { pokerViewModel.previousScreen() },
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = PokerWhite
                )
            ) {
                Icon(Icons.Filled.ArrowBack, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Anterior")
            }
            
            Button(
                onClick = { pokerViewModel.nextScreen() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = PokerGold
                )
            ) {
                Text(
                    "Siguiente",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = PokerBlack
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    Icons.Filled.ArrowForward, 
                    contentDescription = null,
                    tint = PokerBlack,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
fun PlayerBoxCard(
    player: Player,
    onAddPaidBox: () -> Unit,
    onAddUnpaidBox: () -> Unit,
    onRemovePaidBox: () -> Unit,
    onRemoveUnpaidBox: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = PokerGray.copy(alpha = 0.9f)
        )
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = player.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = PokerWhite
                )
                // Remove buttons
                Row {
                    if (player.paidBoxes > 0) {
                        IconButton(
                            onClick = onRemovePaidBox,
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = PokerRed.copy(alpha = 0.8f)
                            )
                        ) {
                            Text("-", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = PokerWhite)
                        }
                    }
                    if (player.unpaidBoxes > 0) {
                        IconButton(
                            onClick = onRemoveUnpaidBox,
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = PokerRed.copy(alpha = 0.8f)
                            )
                        ) {
                            Text("-", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = PokerWhite)
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Cajas pagadas: ${player.paidBoxes}",
                style = MaterialTheme.typography.bodyMedium,
                color = PokerGold
            )
            
            Text(
                text = "Cajas no pagadas: ${player.unpaidBoxes}",
                style = MaterialTheme.typography.bodyMedium,
                color = PokerWhiteLight
            )
            
            Text(
                text = "Total cajas: ${player.totalBoxes}",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = onAddPaidBox,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Agregar Caja Pagada")
                }
                
                Spacer(modifier = Modifier.width(8.dp))
                
                OutlinedButton(
                    onClick = onAddUnpaidBox,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Agregar Caja No Pagada")
                }
            }
        }
    }
}

@Composable
fun FinalCountScreen(
    pokerViewModel: PokerViewModel,
    players: List<Player>
) {
    val totalCollected = players.sumOf { it.totalBoxes * 5000 }
    val totalFinalChips = players.sumOf { it.finalChips }
    val remainingBalance = totalCollected - totalFinalChips
    val isNegative = remainingBalance < 0
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text(
            text = stringResource(R.string.final_count_title),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = PokerWhite
        )
        
        Spacer(modifier = Modifier.height(20.dp))
        
        // Summary card
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (isNegative) PokerRed.copy(alpha = 0.9f) else PokerGold.copy(alpha = 0.9f)
            )
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "💰 RESUMEN DE CAJA",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = if (isNegative) PokerWhite else PokerBlack
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                Text(
                    text = "Total recaudado: $${totalCollected}",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = if (isNegative) PokerWhite else PokerBlack
                )
                
                Text(
                    text = "Fichas finales: ${totalFinalChips}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = if (isNegative) PokerWhite else PokerBlack
                )
                
                Text(
                    text = "Saldo restante: $${remainingBalance}",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = if (isNegative) PokerWhite else PokerBlack
                )
                
                if (isNegative) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "⚠️ ¡ATENCIÓN! El saldo es negativo",
                        style = MaterialTheme.typography.bodyMedium,
                        color = PokerWhite,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            itemsIndexed(players) { index, player ->
                PlayerFinalCountCard(
                    player = player,
                    onUpdateFinalChips = { chips ->
                        pokerViewModel.updatePlayerFinalChips(index, chips)
                    },
                    onUpdatePaymentMethod = { method ->
                        pokerViewModel.updatePlayerPaymentMethod(index, method)
                    }
                )
            }
        }
        
        // Navigation buttons
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedButton(
                onClick = { pokerViewModel.previousScreen() },
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = PokerWhite
                )
            ) {
                Icon(Icons.Filled.ArrowBack, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Anterior")
            }
            
            Button(
                onClick = { pokerViewModel.nextScreen() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = PokerGold
                )
            ) {
                Text(
                    "Siguiente",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = PokerBlack
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    Icons.Filled.ArrowForward, 
                    contentDescription = null,
                    tint = PokerBlack,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerFinalCountCard(
    player: Player,
    onUpdateFinalChips: (Int) -> Unit,
    onUpdatePaymentMethod: (PaymentMethod) -> Unit
) {
    var finalChips by remember { mutableStateOf(player.finalChips.toString()) }
    var expanded by remember { mutableStateOf(false) }
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = PokerGray.copy(alpha = 0.9f)
        )
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = player.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = PokerWhite
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Cajas compradas: ${player.totalBoxes} (${player.totalBoxes * 5000} fichas iniciales)",
                style = MaterialTheme.typography.bodyMedium,
                color = PokerWhiteLight
            )
            
            if (player.unpaidBoxes > 0) {
                Text(
                    text = "Cajas no pagadas: ${player.unpaidBoxes}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = PokerGoldDark
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            TextField(
                value = finalChips,
                onValueChange = { 
                    finalChips = it
                    it.toIntOrNull()?.let { chips -> onUpdateFinalChips(chips) }
                },
                label = { Text(stringResource(R.string.final_chips)) },
                placeholder = { Text(stringResource(R.string.final_chips_hint)) },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = PokerGrayLight.copy(alpha = 0.9f),
                    unfocusedContainerColor = PokerGrayLight.copy(alpha = 0.7f),
                    focusedLabelColor = PokerWhite,
                    unfocusedLabelColor = PokerWhiteLight,
                    focusedTextColor = PokerWhite,
                    unfocusedTextColor = PokerWhite
                ),
                keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                    keyboardType = androidx.compose.ui.text.input.KeyboardType.Number
                )
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                TextField(
                    value = when (player.paymentMethod) {
                        PaymentMethod.CASH -> stringResource(R.string.payment_cash)
                        PaymentMethod.TRANSFER -> stringResource(R.string.payment_transfer)
                    },
                    onValueChange = {},
                    readOnly = true,
                    label = { Text(stringResource(R.string.payment_method)) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = PokerGrayLight.copy(alpha = 0.9f),
                        unfocusedContainerColor = PokerGrayLight.copy(alpha = 0.7f),
                        focusedLabelColor = PokerWhite,
                        unfocusedLabelColor = PokerWhiteLight,
                        focusedTextColor = PokerWhite,
                        unfocusedTextColor = PokerWhite
                    )
                )
                
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text(stringResource(R.string.payment_cash)) },
                        onClick = {
                            onUpdatePaymentMethod(PaymentMethod.CASH)
                            expanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(stringResource(R.string.payment_transfer)) },
                        onClick = {
                            onUpdatePaymentMethod(PaymentMethod.TRANSFER)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun SummaryScreen(
    pokerViewModel: PokerViewModel,
    summary: SessionSummary?
) {
    if (summary == null) return
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = stringResource(R.string.summary_title),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = PokerWhite
        )
        
        Spacer(modifier = Modifier.height(20.dp))
        
        // Date
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(
                containerColor = PokerGray.copy(alpha = 0.9f)
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Fecha: ${summary.date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))}",
                    style = MaterialTheme.typography.titleMedium,
                    color = PokerWhiteLight
                )
            }
        }
        
        Spacer(modifier = Modifier.height(20.dp))
        
        // Players summary
        summary.players
            .map { player ->
                val result = if (player.paidBoxes > 0) {
                    player.finalChips
                } else {
                    player.finalChips - (player.unpaidBoxes * 5000)
                }
                Triple(player, player.totalBoxes * 5000, result)
            }
            .sortedByDescending { it.third }
            .forEach { (player, initialChips, result) ->
                val resultColor = if (result >= 0) PokerGold else PokerRed
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = PokerGray.copy(alpha = 0.9f)
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = player.name,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = PokerWhite
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Cajas totales: ${player.totalBoxes} (${initialChips} fichas)", color = PokerWhiteLight)
                        if (player.unpaidBoxes > 0) {
                            Text(
                                text = "Cajas no pagadas: ${player.unpaidBoxes}",
                                color = PokerGoldDark
                            )
                        }
                        Text("Fichas finales: ${player.finalChips}", color = PokerWhiteLight)
                        Text(
                            text = "Resultado: ${if (result >= 0) "+$result" else "$result"}",
                            color = resultColor,
                            fontWeight = FontWeight.Bold
                        )
                        Text("Pago: ${when (player.paymentMethod) {
                            PaymentMethod.CASH -> stringResource(R.string.payment_cash)
                            PaymentMethod.TRANSFER -> stringResource(R.string.payment_transfer)
                        }}", color = PokerWhiteLight)
                    }
                }
            }
        
        Spacer(modifier = Modifier.height(20.dp))
        
        // Administración de Cajas
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(
                containerColor = PokerGray.copy(alpha = 0.9f)
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "📦 ADMINISTRACIÓN DE CAJAS",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = PokerGold
                )
                Spacer(modifier = Modifier.height(8.dp))
                val totalPaidBoxes = summary.players.sumOf { it.paidBoxes }
                val totalUnpaidBoxes = summary.players.sumOf { it.unpaidBoxes }
                Text(
                    text = "Cajas totales pagadas: $totalPaidBoxes",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = PokerGold
                )
                Text(
                    text = "Cajas totales sin pagar: $totalUnpaidBoxes",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = PokerGoldDark
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Total de cajas: ${totalPaidBoxes + totalUnpaidBoxes}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = PokerWhiteLight
                )
            }
        }
        
        Spacer(modifier = Modifier.height(20.dp))
        
        // Botones de acción
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = { 
                    pokerViewModel.saveCurrentSession(context)
                },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PokerGold
                )
            ) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = "Guardar sesión",
                    tint = PokerBlack
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "💾 Guardar Sesión",
                    fontWeight = FontWeight.Bold,
                    color = PokerBlack
                )
            }
            
            Button(
                onClick = { 
                    pokerViewModel.copySummaryToClipboard(context)
                },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PokerGrayLight
                )
            ) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = "Copiar resumen",
                    tint = PokerWhite
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "📋 Copiar",
                    fontWeight = FontWeight.Bold,
                    color = PokerWhite
                )
            }
        }
        
        Spacer(modifier = Modifier.height(12.dp))
        
        // Botón para nueva sesión
        Button(
            onClick = { pokerViewModel.resetSession() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = PokerRed
            )
        ) {
            Icon(
                Icons.Filled.Refresh,
                contentDescription = "Nueva sesión",
                tint = PokerWhite
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                "🔄 Nueva Sesión",
                fontWeight = FontWeight.Bold,
                color = PokerWhite
            )
        }
    }
}

@Composable
fun HistoryScreen(
    pokerViewModel: PokerViewModel
) {
    val savedSessions by pokerViewModel.savedSessions.collectAsState()
    val currentSessionPlayers by pokerViewModel.currentSessionPlayers.collectAsState()
    val context = LocalContext.current
    
    var searchQuery by remember { mutableStateOf("") }
    var showOnlyProfitable by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        pokerViewModel.loadAllSessions(context)
    }
    
    // Filtrar sesiones
    val filteredSessions = remember(savedSessions, searchQuery, showOnlyProfitable) {
        savedSessions.filter { session ->
            val matchesSearch = searchQuery.isEmpty() || 
                session.date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).contains(searchQuery, ignoreCase = true)
            val matchesFilter = !showOnlyProfitable || session.netResult > 0
            matchesSearch && matchesFilter
        }
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "📅 Historial de Sesiones",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = PokerWhite
            )
            
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = PokerGold.copy(alpha = 0.8f)
                ),
                modifier = Modifier.padding(4.dp)
            ) {
                Text(
                    text = "${filteredSessions.size}/${savedSessions.size} sesiones",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    color = PokerBlack,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Búsqueda y filtros
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = PokerGray.copy(alpha = 0.9f)
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                // Campo de búsqueda
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    label = { Text("Buscar por fecha...") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                // Filtro de sesiones rentables
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = showOnlyProfitable,
                        onCheckedChange = { showOnlyProfitable = it },
                        colors = CheckboxDefaults.colors(
                            checkedColor = PokerGold,
                            uncheckedColor = PokerWhiteLight
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Solo sesiones rentables",
                        style = MaterialTheme.typography.bodyMedium,
                        color = PokerWhite
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        if (filteredSessions.isEmpty()) {
            // Estado vacío
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = PokerGray.copy(alpha = 0.9f)
                )
            ) {
                Column(
                    modifier = Modifier.padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = if (savedSessions.isEmpty()) "📊" else "🔍",
                        fontSize = 48.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = if (savedSessions.isEmpty()) "No hay sesiones guardadas" else "No se encontraron sesiones",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = PokerWhite,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = if (savedSessions.isEmpty()) 
                            "Guarda tu primera sesión de poker para ver el historial aquí"
                        else 
                            "Intenta cambiar los filtros de búsqueda",
                        style = MaterialTheme.typography.bodyMedium,
                        color = PokerWhiteLight,
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            // Lista de sesiones
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredSessions) { session ->
                    SessionCard(
                        session = session,
                        onDelete = { pokerViewModel.deleteSession(session, context) },
                        onViewDetails = { 
                            pokerViewModel.goToSessionDetails(session)
                        }
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(20.dp))
        
        // Botón para volver
        Button(
            onClick = { pokerViewModel.previousScreen() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = PokerGold
            )
        ) {
            Icon(Icons.Filled.ArrowBack, contentDescription = null, tint = PokerBlack)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                "Volver",
                fontWeight = FontWeight.Bold,
                color = PokerBlack
            )
        }
    }
}

@Composable
fun SessionCard(
    session: PokerSession,
    onDelete: () -> Unit,
    onViewDetails: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = PokerGray.copy(alpha = 0.9f)
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Fecha
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = session.date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = PokerGold
                )
                
                IconButton(
                    onClick = onDelete,
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = PokerRed.copy(alpha = 0.8f)
                    )
                ) {
                    Icon(
                        Icons.Filled.Delete,
                        contentDescription = "Eliminar sesión",
                        tint = PokerWhite,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Resultados
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Positivo",
                        style = MaterialTheme.typography.bodySmall,
                        color = PokerWhiteLight
                    )
                    Text(
                        text = "+${session.totalPositive}",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = PokerGold
                    )
                }
                
                Column {
                    Text(
                        text = "Negativo",
                        style = MaterialTheme.typography.bodySmall,
                        color = PokerWhiteLight
                    )
                    Text(
                        text = "-${session.totalNegative}",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = PokerRed
                    )
                }
                
                Column {
                    Text(
                        text = "Neto",
                        style = MaterialTheme.typography.bodySmall,
                        color = PokerWhiteLight
                    )
                    Text(
                        text = if (session.netResult >= 0) "+${session.netResult}" else "${session.netResult}",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = if (session.netResult >= 0) PokerGold else PokerRed
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Botón para ver detalles
            Button(
                onClick = onViewDetails,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PokerGold.copy(alpha = 0.8f)
                )
            ) {
                Icon(
                    Icons.Filled.CalendarToday,
                    contentDescription = "Ver detalles",
                    tint = PokerBlack
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "Ver Detalles",
                    fontWeight = FontWeight.Bold,
                    color = PokerBlack
                )
            }
        }
    }
}

@Composable
fun SessionDetailsScreen(
    pokerViewModel: PokerViewModel,
    session: PokerSession
) {
    val currentSessionPlayers by pokerViewModel.currentSessionPlayers.collectAsState()
    val context = LocalContext.current
    
    LaunchedEffect(Unit) {
        pokerViewModel.loadSessionByDate(session.date, context)
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        // Header con botón de volver
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { pokerViewModel.goBackFromDetails() },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = PokerGold.copy(alpha = 0.8f)
                )
            ) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Volver", tint = PokerBlack)
            }
            
            Text(
                text = "📊 Detalles de Sesión",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = PokerWhite
            )
            
            // Fecha
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = PokerGold.copy(alpha = 0.8f)
                )
            ) {
                Text(
                    text = session.date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    color = PokerBlack,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }
        
        Spacer(modifier = Modifier.height(20.dp))
        
        // Resumen de la sesión
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(
                containerColor = PokerGray.copy(alpha = 0.9f)
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "💰 RESUMEN GENERAL",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = PokerGold
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "Positivo",
                            style = MaterialTheme.typography.bodySmall,
                            color = PokerWhiteLight
                        )
                        Text(
                            text = "+${session.totalPositive}",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold,
                            color = PokerGold
                        )
                    }
                    
                    Column {
                        Text(
                            text = "Negativo",
                            style = MaterialTheme.typography.bodySmall,
                            color = PokerWhiteLight
                        )
                        Text(
                            text = "-${session.totalNegative}",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold,
                            color = PokerRed
                        )
                    }
                    
                    Column {
                        Text(
                            text = "Neto",
                            style = MaterialTheme.typography.bodySmall,
                            color = PokerWhiteLight
                        )
                        Text(
                            text = if (session.netResult >= 0) "+${session.netResult}" else "${session.netResult}",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold,
                            color = if (session.netResult >= 0) PokerGold else PokerRed
                        )
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.height(20.dp))
        
        // Lista de jugadores
        Text(
            text = "👥 Jugadores (${currentSessionPlayers.size})",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = PokerWhite
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        if (currentSessionPlayers.isEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = PokerGray.copy(alpha = 0.9f)
                )
            ) {
                Column(
                    modifier = Modifier.padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "⏳",
                        fontSize = 48.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Cargando jugadores...",
                        style = MaterialTheme.typography.bodyMedium,
                        color = PokerWhiteLight,
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(currentSessionPlayers) { player ->
                    PlayerDetailCard(player = player)
                }
            }
        }
        
        Spacer(modifier = Modifier.height(20.dp))
        
        // Botón para copiar detalles
        Button(
            onClick = { 
                copySessionDetailsToClipboard(session, currentSessionPlayers, context)
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = PokerGold.copy(alpha = 0.8f)
            )
        ) {
            Icon(
                Icons.Filled.Add,
                contentDescription = "Copiar detalles",
                tint = PokerBlack
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                "📋 Copiar Detalles",
                fontWeight = FontWeight.Bold,
                color = PokerBlack
            )
        }
    }
}

@Composable
fun PlayerDetailCard(player: PokerPlayer) {
    val result = player.result
    val resultColor = if (result >= 0) PokerGold else PokerRed
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = PokerGray.copy(alpha = 0.9f)
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = player.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = PokerWhite
                )
                
                Text(
                    text = if (result >= 0) "+$result" else "$result",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = resultColor
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Cajas pagadas",
                        style = MaterialTheme.typography.bodySmall,
                        color = PokerWhiteLight
                    )
                    Text(
                        text = "${player.paidBoxes}",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = PokerGold
                    )
                }
                
                Column {
                    Text(
                        text = "Cajas sin pagar",
                        style = MaterialTheme.typography.bodySmall,
                        color = PokerWhiteLight
                    )
                    Text(
                        text = "${player.unpaidBoxes}",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = PokerRed
                    )
                }
                
                Column {
                    Text(
                        text = "Fichas finales",
                        style = MaterialTheme.typography.bodySmall,
                        color = PokerWhiteLight
                    )
                    Text(
                        text = "${player.finalChips}",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = PokerWhite
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Método de pago:",
                    style = MaterialTheme.typography.bodySmall,
                    color = PokerWhiteLight
                )
                
                Text(
                    text = if (player.paymentMethod == "CASH") "Efectivo" else "Transferencia",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = PokerGold
                )
            }
        }
    }
}

private fun copySessionDetailsToClipboard(
    session: PokerSession,
    players: List<PokerPlayer>,
    context: Context
) {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val dateStr = session.date.format(formatter)
    
    val detailsText = buildString {
        appendLine("📊 DETALLES POKER - $dateStr")
        appendLine("=".repeat(40))
        appendLine()
        
        appendLine("💰 RESUMEN:")
        appendLine("   Positivo: +${session.totalPositive}")
        appendLine("   Negativo: -${session.totalNegative}")
        appendLine("   Neto: ${if (session.netResult >= 0) "+${session.netResult}" else "${session.netResult}"}")
        appendLine()
        
        appendLine("👥 JUGADORES:")
        players.forEach { player ->
            val resultStr = if (player.result >= 0) "+${player.result}" else "${player.result}"
            val paymentStr = if (player.paymentMethod == "CASH") "Efectivo" else "Transferencia"
            
            appendLine("   👤 ${player.name}")
            if (player.paidBoxes > 0) {
                appendLine("      Cajas pagadas: ${player.paidBoxes}")
            }
            if (player.unpaidBoxes > 0) {
                appendLine("      Cajas sin pagar: ${player.unpaidBoxes}")
            }
            appendLine("      Fichas finales: ${player.finalChips}")
            appendLine("      Resultado: $resultStr")
            appendLine("      Pago: $paymentStr")
            appendLine()
        }
    }
    
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
    val clip = android.content.ClipData.newPlainText("Detalles Poker", detailsText)
    clipboard.setPrimaryClip(clip)
}