package com.lemobs_sigelu.gestao_estoques.bd

import com.j256.ormlite.dao.BaseDaoImpl
import com.j256.ormlite.dao.Dao
import com.j256.ormlite.support.ConnectionSource
import com.lemobs_sigelu.gestao_estoques.bd_model.PedidoDTO

class PedidoDAO : BaseDaoImpl<PedidoDTO, Integer> {

    private enum class Columns(val nome: String) {
        ID("id")
    }

    companion object {
        lateinit var dao: Dao<PedidoDTO, Int>
    }

    constructor(cs: ConnectionSource) : super(PedidoDTO::class.java) {
        connectionSource = cs
        initialize()
    }

    init {
        dao = DatabaseHelper.getDao(PedidoDTO::class.java)
    }

    fun add(table: PedidoDTO) = dao.createOrUpdate(table)

    fun queryForId(id: Int): PedidoDTO? {
        return dao.queryForId(id)
    }
}