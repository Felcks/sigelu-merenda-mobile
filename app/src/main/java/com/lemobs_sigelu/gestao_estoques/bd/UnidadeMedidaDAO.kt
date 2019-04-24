package com.lemobs_sigelu.gestao_estoques.bd

import com.j256.ormlite.dao.BaseDaoImpl
import com.j256.ormlite.dao.Dao
import com.j256.ormlite.support.ConnectionSource
import com.lemobs_sigelu.gestao_estoques.bd_model.UnidadeMedidaDTO

class UnidadeMedidaDAO : BaseDaoImpl<UnidadeMedidaDTO, Integer> {

    private enum class Columns(val nome: String) {
        ID("id")
    }

    companion object {
        lateinit var dao: Dao<UnidadeMedidaDTO, Int>
    }

    constructor(cs: ConnectionSource) : super(UnidadeMedidaDTO::class.java) {
        connectionSource = cs
        initialize()
    }

    init {
        dao = DatabaseHelper.getDao(UnidadeMedidaDTO::class.java)
    }

    fun add(table: UnidadeMedidaDTO) = dao.createOrUpdate(table)

    fun queryForId(id: Int): UnidadeMedidaDTO? {
        return dao.queryForId(id)
    }
}