package com.lemobs_sigelu.gestao_estoques.bd

import com.j256.ormlite.dao.BaseDaoImpl
import com.j256.ormlite.dao.Dao
import com.j256.ormlite.support.ConnectionSource
import com.lemobs_sigelu.gestao_estoques.bd_model.SituacaoDTO

class SituacaoDAO : BaseDaoImpl<SituacaoDTO, Integer> {

    private enum class Columns(val nome: String) {
        ID("id")
    }

    companion object {
        lateinit var dao: Dao<SituacaoDTO, Int>
    }

    constructor(cs: ConnectionSource) : super(SituacaoDTO::class.java) {
        connectionSource = cs
        initialize()
    }

    init {
        dao = DatabaseHelper.getDao(SituacaoDTO::class.java)
    }

    fun add(table: SituacaoDTO) = dao.createOrUpdate(table)

    fun queryForId(id: Int): SituacaoDTO? {
        return dao.queryForId(id)
    }
}