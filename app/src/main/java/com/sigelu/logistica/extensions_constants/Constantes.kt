package com.sigelu.logistica.extensions_constants

import androidx.room.Room
import com.sigelu.logistica.App
import com.sigelu.logistica.BuildConfig
import com.sigelu.logistica.bd_room.AppDatabase

/**
 * Created by felcks on Jun, 2019
 */

val db = Room.databaseBuilder(
    App.instance,
        AppDatabase::class.java, "${BuildConfig.APPLICATION_ID}.db"
    )
    .fallbackToDestructiveMigration() //Destrói toda a database se o schema do banco não estiver compatível -> drop table all
    .allowMainThreadQueries() //Queries na main thread
    .build()

const val SITUACAO_RASCUNHO = 1
const val SITUACAO_EM_ANALISE_ID = 2
const val SITUACAO_CORRECAO_SOLICITADA = 3
const val SITUACAO_APROVADO_ID = 4
const val SITUACAO_REPROVADO_ID = 5
const val SITUACAO_PARCIAL_ID = 6
const val SITUACAO_ENTREGUE_ID = 7
const val SITUACAO_CANCELADO_ID = 8


/* Formato Mascara */
const val MASCARA_HORARIO = "##:##"
const val MASCARA_DATA = "##/##/####"

/* Tipo Origem */
const val TIPO_LOCAL_ALMOXARIFADO = 1
const val TIPO_LOCAL_NUCLEO = 2
const val TIPO_LOCAL_OBRA = 3


/* Tipo Estoque */
const val TIPO_ESTOQUE_ALMOXARIFADO = 1
const val TIPO_ESTOQUE_NUCLEO = 2
const val TIPO_ESTOQUE_OBRA = 3
const val TIPO_ESTOQUE_FORNECEDOR = 4

/* Nomes */
const val NOME_ALMOXARIFADO = "Almoxarifado"
const val NOME_NUCLEO = "Núcleo"
const val NOME_OBRA = "Obra"
const val NOME_FORNECEDOR = "Fornecedor"

fun getNomeDoTipo(tipoEstoqueID: Int): String{

    return when(tipoEstoqueID){
        TIPO_ESTOQUE_ALMOXARIFADO -> NOME_ALMOXARIFADO
        TIPO_ESTOQUE_NUCLEO -> NOME_NUCLEO
        TIPO_ESTOQUE_OBRA -> NOME_OBRA
        TIPO_ESTOQUE_FORNECEDOR -> NOME_FORNECEDOR
        else -> ""
    }
}
