package dev.nocturnbinary.dzikirkita.features.asmaulhusna.data

import android.content.Context
import dev.nocturnbinary.dzikirkita.R

data class AsmaulHusna(
    val asmaulHusna: String,
    val asmaulHusnaMakna: String,
)

object AsmaulHusnaData {
    fun getAsmaulHusna(context: Context): List<AsmaulHusna> {
        val dataAsmaul = context.resources.getStringArray(R.array.data_asmaulHusna)
        val dataMaknaAsmaul = context.resources.getStringArray(R.array.data_asmaul_makna)
        val listAsmaul = ArrayList<AsmaulHusna>()
        for (i in dataAsmaul.indices) {
            val asmaul = AsmaulHusna(dataAsmaul[i], dataMaknaAsmaul[i])
            listAsmaul.add(asmaul)
        }
        return listAsmaul
    }
}