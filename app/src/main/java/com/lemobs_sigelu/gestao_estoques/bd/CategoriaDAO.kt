package com.lemobs_sigelu.gestao_estoques.bd

import com.j256.ormlite.dao.BaseDaoImpl
import com.j256.ormlite.dao.Dao
import com.j256.ormlite.support.ConnectionSource
import com.lemobs_sigelu.gestao_estoques.bd_model.CategoriaDTO

/**
 * Created by felcks on Jun, 2019
 */
class CategoriaDAO : BaseDaoImpl<CategoriaDTO, Integer> {

    private enum class Columns (val nome: String) {
        ID("id"),
        PEDIDO_ID("pedido_id")
    }

    companion object {
        lateinit var dao: Dao<CategoriaDTO, Int>
    }

    constructor(cs: ConnectionSource) : super(CategoriaDTO::class.java) {
        connectionSource = cs
        initialize()
    }

    init {
        dao = DatabaseHelper.getDao(CategoriaDTO::class.java)
    }

    fun add(table: CategoriaDTO) = dao.createOrUpdate(table)

    fun queryForId(id: Int): CategoriaDTO? {
        return dao.queryForId(id)
    }
}