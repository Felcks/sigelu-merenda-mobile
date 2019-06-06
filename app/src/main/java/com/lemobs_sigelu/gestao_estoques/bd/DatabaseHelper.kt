package com.lemobs_sigelu.gestao_estoques.bd

import android.database.sqlite.SQLiteDatabase
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
import com.j256.ormlite.support.ConnectionSource
import com.j256.ormlite.table.TableUtils
import com.lemobs_sigelu.gestao_estoques.App
import com.lemobs_sigelu.gestao_estoques.BuildConfig
import com.lemobs_sigelu.gestao_estoques.bd_model.*
import java.util.*

object DatabaseHelper : OrmLiteSqliteOpenHelper(App.instance, "${BuildConfig.APPLICATION_ID}.db", null, 1) {

    override fun onCreate(database: SQLiteDatabase?, connectionSource: ConnectionSource?) {

        TableUtils.createTableIfNotExists(connectionSource, CategoriaDTO::class.java)
        TableUtils.createTableIfNotExists(connectionSource, EnvioDTO::class.java)
        TableUtils.createTableIfNotExists(connectionSource, ItemEnvioDTO::class.java)
        TableUtils.createTableIfNotExists(connectionSource, ItemEstoqueDTO::class.java)
        TableUtils.createTableIfNotExists(connectionSource, MaterialDTO::class.java)
        TableUtils.createTableIfNotExists(connectionSource, MaterialDeCadastroDTO::class.java)
        TableUtils.createTableIfNotExists(connectionSource, MaterialDePedidoDTO::class.java)
        TableUtils.createTableIfNotExists(connectionSource, MaterialDeSituacaoDTO::class.java)
        TableUtils.createTableIfNotExists(connectionSource, PedidoDTO::class.java)
        TableUtils.createTableIfNotExists(connectionSource, SituacaoDTO::class.java)
        TableUtils.createTableIfNotExists(connectionSource, SituacaoHistoricoDTO::class.java)
        TableUtils.createTableIfNotExists(connectionSource, UnidadeMedidaDTO::class.java)
    }

    override fun onUpgrade(database: SQLiteDatabase?, connectionSource: ConnectionSource?, oldVersion: Int, newVersion: Int) {

        TableUtils.dropTable<CategoriaDTO, Any>(connectionSource, CategoriaDTO::class.java, true)
        TableUtils.dropTable<EnvioDTO, Any>(connectionSource, EnvioDTO::class.java, true)
        TableUtils.dropTable<ItemEnvioDTO, Any>(connectionSource, ItemEnvioDTO::class.java, true)
        TableUtils.dropTable<ItemEstoqueDTO, Any>(connectionSource, ItemEstoqueDTO::class.java, true)
        TableUtils.dropTable<MaterialDTO, Any>(connectionSource, MaterialDTO::class.java, true)
        TableUtils.dropTable<MaterialDeCadastroDTO, Any>(connectionSource, MaterialDeCadastroDTO::class.java, true)
        TableUtils.dropTable<MaterialDePedidoDTO, Any>(connectionSource, MaterialDePedidoDTO::class.java, true)
        TableUtils.dropTable<MaterialDeSituacaoDTO, Any>(connectionSource, MaterialDeSituacaoDTO::class.java, true)
        TableUtils.dropTable<PedidoDTO, Any>(connectionSource, PedidoDTO::class.java, true)
        TableUtils.dropTable<SituacaoDTO, Any>(connectionSource, SituacaoDTO::class.java, true)
        TableUtils.dropTable<SituacaoHistoricoDTO, Any>(connectionSource, SituacaoHistoricoDTO::class.java, true)
        TableUtils.dropTable<UnidadeMedidaDTO, Any>(connectionSource, UnidadeMedidaDTO::class.java, true)
    }

}