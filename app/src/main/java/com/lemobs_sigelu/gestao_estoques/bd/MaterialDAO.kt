package com.lemobs_sigelu.gestao_estoques.bd

import com.j256.ormlite.dao.BaseDaoImpl
import com.j256.ormlite.dao.Dao
import com.j256.ormlite.support.ConnectionSource
import com.lemobs_sigelu.gestao_estoques.bd_model.MaterialDTO

class MaterialDAO : BaseDaoImpl<MaterialDTO, Integer> {

    private enum class Columns(val nome: String) {
        ID("id")
    }

    companion object {
        lateinit var dao: Dao<MaterialDTO, Int>
    }

    constructor(cs: ConnectionSource) : super(MaterialDTO::class.java) {
        connectionSource = cs
        initialize()
    }

    init {
        dao = DatabaseHelper.getDao(MaterialDTO::class.java)
    }

    fun add(table: MaterialDTO) = dao.createOrUpdate(table)

    fun queryForId(id: Int): MaterialDTO? {
        return dao.queryForId(id)
    }
}