package com.lemobs_sigelu.gestao_estoques.bd

import android.database.sqlite.SQLiteDatabase
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
import com.j256.ormlite.support.ConnectionSource
import com.j256.ormlite.table.TableUtils
import com.lemobs_sigelu.gestao_estoques.App
import com.lemobs_sigelu.gestao_estoques.BuildConfig
import com.lemobs_sigelu.gestao_estoques.bd_model.PedidoDTO
import com.lemobs_sigelu.gestao_estoques.bd_model.SituacaoDTO
import java.util.*

object DatabaseHelper : OrmLiteSqliteOpenHelper(App.instance, "${BuildConfig.APPLICATION_ID}.db", null, 1) {

    override fun onCreate(database: SQLiteDatabase?, connectionSource: ConnectionSource?) {
        TableUtils.createTableIfNotExists(connectionSource, PedidoDTO::class.java)
        TableUtils.createTableIfNotExists(connectionSource, SituacaoDTO::class.java)

    }

    override fun onUpgrade(database: SQLiteDatabase?, connectionSource: ConnectionSource?, oldVersion: Int, newVersion: Int) {
        TableUtils.dropTable<PedidoDTO, Any>(connectionSource, PedidoDTO::class.java, true)
        TableUtils.dropTable<SituacaoDTO, Any>(connectionSource, SituacaoDTO::class.java, true)
    }

}