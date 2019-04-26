package com.lemobs_sigelu.gestao_estoques.bd

import com.j256.ormlite.dao.BaseDaoImpl
import com.j256.ormlite.dao.Dao
import com.j256.ormlite.support.ConnectionSource
import com.lemobs_sigelu.gestao_estoques.bd_model.MaterialDeCadastroDTO

class MaterialDeCadastroDAO : BaseDaoImpl<MaterialDeCadastroDTO, Integer> {

    private enum class Columns(val nome: String) {
        ID("id")
    }

    companion object {
        lateinit var dao: Dao<MaterialDeCadastroDTO, Int>
    }

    constructor(cs: ConnectionSource) : super(MaterialDeCadastroDTO::class.java) {
        connectionSource = cs
        initialize()
    }

    init {
        dao = DatabaseHelper.getDao(MaterialDeCadastroDTO::class.java)
    }

    fun add(table: MaterialDeCadastroDTO) = dao.createOrUpdate(table)

    fun queryForId(id: Int): MaterialDeCadastroDTO? {
        return dao.queryForId(id)
    }
}