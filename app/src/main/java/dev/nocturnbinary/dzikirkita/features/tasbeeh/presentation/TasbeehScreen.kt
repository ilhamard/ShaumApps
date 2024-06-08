package dev.nocturnbinary.dzikirkita.features.tasbeeh.presentation

import android.content.Context
import android.media.MediaPlayer
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Repeat
import androidx.compose.material.icons.outlined.Vibration
import androidx.compose.material.icons.outlined.VolumeUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import dev.nocturnbinary.dzikirkita.R
import dev.nocturnbinary.dzikirkita.ui.theme.background
import dev.nocturnbinary.dzikirkita.ui.theme.primary
import dev.nocturnbinary.dzikirkita.utils.DzikirKitaPreview

@Composable
fun TasbeehScreen(viewModel: TasbeehViewModel, navController: NavController) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Tasbeeh(
        uiState = uiState,
        onEvent = { event ->
            viewModel.onEvent(event)
        },
        navigateBack = {
            navController.popBackStack()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Tasbeeh(
    uiState: TasbeehUiState,
    onEvent: (TasbeehUiEvent) -> Unit,
    navigateBack: () -> Unit,
) {
    val context = LocalContext.current

    val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val vibratorManager =
            context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
        vibratorManager.defaultVibrator
    } else {
        @Suppress("DEPRECATION")
        context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }

    val mediaPlayer = remember {
        MediaPlayer.create(context, R.raw.tick)
    }

    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer.release()
        }
    }

    if (uiState.tasbeehCount == uiState.targetCount && uiState.isVibrateAlert) {
        vibrationAlert(vibrator)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Tasbeeh") },
                actions = {
                    IconButton(onClick = { onEvent(TasbeehUiEvent.EditTargetCount) }) {
                        Icon(
                            imageVector = Icons.Outlined.Edit,
                            contentDescription = "Ubah Target",
                            tint = Color.Gray
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Kembali"
                        )
                    }
                }
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    progress = uiState.progressCount,
                    color = primary,
                    strokeWidth = 12.dp,
                    trackColor = primary.copy(alpha = 0.3f),
                    strokeCap = StrokeCap.Round,
                    modifier = Modifier
                        .size(270.dp)
                        .clickable(
                            interactionSource = MutableInteractionSource(),
                            indication = null
                        ) {
                            onEvent(TasbeehUiEvent.IncrementTasbeehCount)
                            if (uiState.isSoundAlert) {
                                mediaPlayer.start()
                            }
                            if (uiState.tasbeehCount == uiState.targetCount) {
                                onEvent(TasbeehUiEvent.ResetTasbeehCount)
                            }
                        }
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = uiState.tasbeehCount.toString(),
                        style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = "/ ${uiState.targetCount}",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                IconButton(onClick = { onEvent(TasbeehUiEvent.ToggleSoundAlert) }) {
                    Icon(
                        imageVector = Icons.Outlined.VolumeUp,
                        contentDescription = "Suara",
                        tint = Color.Gray,
                        modifier = Modifier
                            .size(32.dp)
                            .background(
                                color = if (uiState.isSoundAlert) Color.Gray.copy(alpha = 0.3f) else Color.Transparent,
                                shape = MaterialTheme.shapes.small
                            )
                    )
                }
                IconButton(onClick = { onEvent(TasbeehUiEvent.ToggleVibrateAlert) }) {
                    Icon(
                        imageVector = Icons.Outlined.Vibration,
                        contentDescription = "Getar",
                        tint = Color.Gray,
                        modifier = Modifier
                            .size(32.dp)
                            .background(
                                color = if (uiState.isVibrateAlert) Color.Gray.copy(alpha = 0.3f) else Color.Transparent,
                                shape = MaterialTheme.shapes.small
                            )
                    )
                }
                IconButton(onClick = { onEvent(TasbeehUiEvent.ResetTasbeehCount) }) {
                    Icon(
                        imageVector = Icons.Outlined.Repeat,
                        contentDescription = "Reset",
                        tint = Color.Gray,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }

        if (uiState.isEditMode) {
            SetTargetDialog(
                targetCount = uiState.newTargetCount,
                onValueChange = { newTargetCount ->
                    onEvent(TasbeehUiEvent.NewTargetCountChanged(newTargetCount))
                },
                onDismiss = { onEvent(TasbeehUiEvent.EditTargetCount) },
                onConfirm = { targetCount ->
                    onEvent(TasbeehUiEvent.ConfirmEditTargetCount(targetCount))
                }
            )
        }
    }
}

@Composable
private fun SetTargetDialog(
    targetCount: String,
    onValueChange: (String) -> Unit,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Ubah Jumlah Hitungan",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.fillMaxWidth()
            )
        },
        text = {
            OutlinedTextField(
                value = targetCount,
                onValueChange = { onValueChange(it) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = background,
                    unfocusedContainerColor = background,
                    focusedBorderColor = primary,
                    unfocusedBorderColor = primary
                ),
                shape = MaterialTheme.shapes.medium,
            )
        },
        confirmButton = {
            Button(
                onClick = { onConfirm(targetCount) },
                colors = ButtonDefaults.buttonColors(containerColor = primary),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(text = "Simpan")
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = onDismiss,
                colors = ButtonDefaults.outlinedButtonColors(contentColor = primary),
                border = BorderStroke(width = 1.dp, color = primary),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(text = "Batal")
            }
        },
        containerColor = background,
        shape = MaterialTheme.shapes.large
    )
}

private fun vibrationAlert(vibrator: Vibrator) {
    vibrator.cancel()

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE))
    } else {
        @Suppress("DEPRECATION")
        vibrator.vibrate(100)
    }
}

@Composable
@DzikirKitaPreview
private fun TasbeehPreview() {
    Tasbeeh(
        uiState = TasbeehUiState(tasbeehCount = 10, targetCount = 33),
        onEvent = {},
        navigateBack = {})
}

@Composable
@Preview
private fun SetTargetDialogPreview() {
    SetTargetDialog(
        targetCount = "33",
        onValueChange = {},
        onDismiss = {},
        onConfirm = {}
    )
}