package dev.nocturnbinary.dzikirkita.features.asmaulhusna.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.nocturnbinary.dzikirkita.features.asmaulhusna.domain.GetAsmaulHusnaUseCase
import javax.inject.Inject

@HiltViewModel
class AsmaulHusnaViewModel @Inject constructor(
    private val getAsmaulHusnaUseCase: GetAsmaulHusnaUseCase,
) : ViewModel() {

    val asmahusna = getAsmaulHusnaUseCase.getAsmaulHusna()
}