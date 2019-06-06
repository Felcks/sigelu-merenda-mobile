package com.lemobs_sigelu.gestao_estoques.bd

import com.j256.ormlite.dao.BaseDaoImpl
import com.j256.ormlite.dao.Dao
import com.j256.ormlite.stmt.QueryBuilder
import com.j256.ormlite.stmt.SelectArg
import com.j256.ormlite.stmt.Where
import com.j256.ormlite.support.ConnectionSource
import com.lemobs_sigelu.gestao_estoques.bd_model.ItemEnvioDTO

/**
 * Created by felcks on Jun, 2019
 */
class ItemEnvioDAO : BaseDaoImpl<ItemEnvioDTO, Integer> {

    private enum class Columns (val nome: String) {
        ID("id"),
        ENVIO_ID("envio_id")
    }

    companion object {
        lateinit var dao: Dao<ItemEnvioDTO, Int>
    }

    constructor(cs: ConnectionSource) : super(ItemEnvioDTO::class.java) {
        connectionSource = cs
        initialize()
    }

    init {
        dao = DatabaseHelper.getDao(ItemEnvioDTO::class.java)
    }

    fun add(table: ItemEnvioDTO) = dao.createOrUpdate(table)

    fun queryForId(id: Int): ItemEnvioDTO? {
        return dao.queryForId(id)
    }

    fun queryForTodosItensDeEnvio(envioID : Int): List<ItemEnvioDTO>{

        val queryBuilder: QueryBuilder<ItemEnvioDTO, Int> = dao.queryBuilder()
        val where: Where<ItemEnvioDTO, Int> = queryBuilder.where()

        val argEnvioID = SelectArg()
        argEnvioID.setValue(envioID)

        where.eq(Columns.ENVIO_ID.nome, argEnvioID)

        return queryBuilder.query()
    }
}