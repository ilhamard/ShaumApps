package dev.nocturnbinary.dzikirkita.features.tasbeeh

data class TasbeehUiState(
    val tasbeehCount: Int = 0,
    val targetCount: Int = 33,
    val newTargetCount: String = "0",
    val progressCount: Float = 0f,
    val isVibrateAlert: Boolean = false,
    val isSoundAlert: Boolean = false,
    val isEditMode: Boolean = false,
)