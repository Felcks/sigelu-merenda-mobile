package com.lemobs_sigelu.gestao_estoques.bd

import com.j256.ormlite.dao.BaseDaoImpl
import com.j256.ormlite.dao.Dao
import com.j256.ormlite.stmt.QueryBuilder
import com.j256.ormlite.stmt.SelectArg
import com.j256.ormlite.stmt.Where
import com.j256.ormlite.support.ConnectionSource
import com.lemobs_sigelu.gestao_estoques.bd_model.EnvioDTO

/**
 * Created by felcks on Jun, 2019
 */
class EnvioDAO : BaseDaoImpl<EnvioDTO, Integer> {

    private enum class Columns (val nome: String) {
        ID("id"),
        PEDIDO_ID("pedido_id")
    }

    companion object {
        lateinit var dao: Dao<EnvioDTO, Int>
    }

    constructor(cs: ConnectionSource) : super(EnvioDTO::class.java) {
        connectionSource = cs
        initialize()
    }

    init {
        dao = DatabaseHelper.getDao(EnvioDTO::class.java)
    }

    fun add(table: EnvioDTO) = dao.createOrUpdate(table)

    fun queryForId(id: Int): EnvioDTO? {
        return dao.queryForId(id)
    }

    fun queryForTodosEnviosDePedido(pedidoID: Int): List<EnvioDTO>{

        val queryBuilder: QueryBuilder<EnvioDTO, Int> = dao.queryBuilder()
        val where: Where<EnvioDTO, Int> = queryBuilder.where()

        val argPedidoID = SelectArg()
        argPedidoID.setValue(pedidoID)

        where.eq(Columns.PEDIDO_ID.nome, argPedidoID)

        return queryBuilder.query()
    }
}