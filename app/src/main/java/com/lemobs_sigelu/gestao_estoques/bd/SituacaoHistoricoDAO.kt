package com.lemobs_sigelu.gestao_estoques.bd

import com.j256.ormlite.dao.BaseDaoImpl
import com.j256.ormlite.dao.Dao
import com.j256.ormlite.support.ConnectionSource
import com.lemobs_sigelu.gestao_estoques.bd_model.SituacaoHistoricoDTO

class SituacaoHistoricoDAO : BaseDaoImpl<SituacaoHistoricoDTO, Integer> {

    private enum class Columns(val nome: String) {
        ID("id")
    }

    companion object {
        lateinit var dao: Dao<SituacaoHistoricoDTO, Int>
    }

    constructor(cs: ConnectionSource) : super(SituacaoHistoricoDTO::class.java) {
        connectionSource = cs
        initialize()
    }

    init {
        dao = DatabaseHelper.getDao(SituacaoHistoricoDTO::class.java)
    }

    fun add(table: SituacaoHistoricoDTO) = dao.createOrUpdate(table)

    fun queryForId(id: Int): SituacaoHistoricoDTO? {
        return dao.queryForId(id)
    }
}