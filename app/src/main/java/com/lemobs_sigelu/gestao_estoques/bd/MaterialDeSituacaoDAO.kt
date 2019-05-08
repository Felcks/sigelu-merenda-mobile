package com.lemobs_sigelu.gestao_estoques.bd

import com.j256.ormlite.dao.BaseDaoImpl
import com.j256.ormlite.dao.Dao
import com.j256.ormlite.support.ConnectionSource
import com.lemobs_sigelu.gestao_estoques.bd_model.MaterialDeSituacaoDTO

class MaterialDeSituacaoDAO : BaseDaoImpl<MaterialDeSituacaoDTO, Integer> {

    private enum class Columns(val nome: String) {
        ID("id")
    }

    companion object {
        lateinit var dao: Dao<MaterialDeSituacaoDTO, Int>
    }

    constructor(cs: ConnectionSource) : super(MaterialDeSituacaoDTO::class.java) {
        connectionSource = cs
        initialize()
    }

    init {
        dao = DatabaseHelper.getDao(MaterialDeSituacaoDTO::class.java)
    }

    fun add(table: MaterialDeSituacaoDTO) = dao.createOrUpdate(table)

    fun queryForId(id: Int): MaterialDeSituacaoDTO? {
        return dao.queryForId(id)
    }

}