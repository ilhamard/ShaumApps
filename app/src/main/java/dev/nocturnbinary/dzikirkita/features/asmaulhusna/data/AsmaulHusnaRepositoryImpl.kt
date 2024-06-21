package dev.nocturnbinary.dzikirkita.features.asmaulhusna.data

import android.content.Context
import javax.inject.Inject

class AsmaulHusnaRepositoryImpl @Inject constructor(
    private val context: Context,
) : AsmaulHusnaRepository {
    override fun getAsmaulHusna(): List<AsmaulHusna> {
        return AsmaulHusnaData.getAsmaulHusna(context)
    }
}