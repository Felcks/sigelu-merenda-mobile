package com.sigelu.merenda.common.domain.model

import com.sigelu.merenda.R
import com.sigelu.merenda.extensions_constants.toDate
import java.util.*

open class Obra(var id: Int,
                var codigo: String,
                val distancia: String,
                val conclusaoPrevista: String,
                var situacao: String,
                val endereco: String,
                val tipo: String,
                val estoqueID: Int,
                val encarregado: Encarregado){


    fun getTitulo(): String{
        return "${this.codigo} - ${this.encarregado.nome} / ${this.endereco}"
    }

    fun getStatusDeConclusao(): String{

        var dias: Long = 0
        var text = ""
        var atrasada = false

        if (this.situacao == "Em andamento") {

            val cal = Calendar.getInstance()
            cal.set(Calendar.HOUR_OF_DAY, 0)
            cal.set(Calendar.MINUTE, 0)
            cal.set(Calendar.SECOND, 0)
            cal.set(Calendar.MILLISECOND, 0)
            val dataHojeZeroHoras = cal.time

            val dataParametro = this.conclusaoPrevista.toDate()
            dataParametro.minutes = 0
            dataParametro.hours = 0
            dataParametro.seconds = 0

            val distancia = (dataParametro.time - dataHojeZeroHoras.time).toDouble()
            var d = (distancia / 1000.0 / 60.0 / 60.0 / 24.0)
            dias = Math.abs(d).toLong()

            when {
                d.compareTo(0) == 0 -> {
                    text = String.format("Previsão de conclusão hoje")
                    atrasada = false
                }
                d > 0 -> {
                    text = String.format("Previsão de conclusão em %1\$s dia(s)", dias)
                    atrasada = false
                }
                d < 0 -> {
                    text = String.format("Previsão de conclusão atrasada em %1\$s dia(s)", dias)
                    atrasada = true
                }
            }
        }
        else{
            text = "Obra Paralisada"
        }

        return text
    }

    fun getColor(): Int {

        var color = R.color.obra_andamento

        if (situacao == "Em andamento") {
            color = R.color.obra_andamento
        }
        else if (situacao == "Paralisada") {
            color = R.color.obra_pausada
        }

        return color
    }

    companion object{
         enum class Situacao {

        }
    }

}