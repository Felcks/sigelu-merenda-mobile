package com.lemobs_sigelu.gestao_estoques.extensions_constants

import androidx.room.Room
import com.lemobs_sigelu.gestao_estoques.App
import com.lemobs_sigelu.gestao_estoques.BuildConfig
import com.lemobs_sigelu.gestao_estoques.bd_room.AppDatabase

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


/* Formato Mascara */
const val MASCARA_HORARIO = "##:##"
const val MASCARA_DATA = "##/##/####"