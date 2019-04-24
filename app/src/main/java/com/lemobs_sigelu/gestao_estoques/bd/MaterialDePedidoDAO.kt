package com.lemobs_sigelu.gestao_estoques.bd

import com.j256.ormlite.dao.BaseDaoImpl
import com.j256.ormlite.dao.Dao
import com.j256.ormlite.support.ConnectionSource
import com.lemobs_sigelu.gestao_estoques.bd_model.MaterialDePedidoDTO

class MaterialDePedidoDAO : BaseDaoImpl<MaterialDePedidoDTO, Integer> {

    private enum class Columns(val nome: String) {
        ID("id")
    }

    companion object {
        lateinit var dao: Dao<MaterialDePedidoDTO, Int>
    }

    constructor(cs: ConnectionSource) : super(MaterialDePedidoDTO::class.java) {
        connectionSource = cs
        initialize()
    }

    init {
        dao = DatabaseHelper.getDao(MaterialDePedidoDTO::class.java)
    }

    fun add(table: MaterialDePedidoDTO) = dao.createOrUpdate(table)

    fun queryForId(id: Int): MaterialDePedidoDTO? {
        return dao.queryForId(id)
    }
}