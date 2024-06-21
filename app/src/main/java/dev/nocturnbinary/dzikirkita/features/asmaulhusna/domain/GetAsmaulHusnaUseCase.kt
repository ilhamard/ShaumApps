package dev.nocturnbinary.dzikirkita.features.asmaulhusna.domain

import dev.nocturnbinary.dzikirkita.features.asmaulhusna.data.AsmaulHusnaRepository
import javax.inject.Inject

class GetAsmaulHusnaUseCase @Inject constructor(
    private val repository: AsmaulHusnaRepository,
) {
    fun getAsmaulHusna() = repository.getAsmaulHusna()
}