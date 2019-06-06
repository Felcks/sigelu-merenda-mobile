package com.lemobs_sigelu.gestao_estoques.bd

import com.j256.ormlite.dao.BaseDaoImpl
import com.j256.ormlite.dao.Dao
import com.j256.ormlite.support.ConnectionSource
import com.lemobs_sigelu.gestao_estoques.bd_model.ItemEstoqueDTO

/**
 * Created by felcks on Jun, 2019
 */
class ItemEstoqueDAO : BaseDaoImpl<ItemEstoqueDTO, Integer> {

    private enum class Columns (val nome: String) {
        ID("id"),
        PEDIDO_ID("pedido_id")
    }

    companion object {
        lateinit var dao: Dao<ItemEstoqueDTO, Int>
    }

    constructor(cs: ConnectionSource) : super(ItemEstoqueDTO::class.java) {
        connectionSource = cs
        initialize()
    }

    init {
        dao = DatabaseHelper.getDao(ItemEstoqueDTO::class.java)
    }

    fun add(table: ItemEstoqueDTO) = dao.createOrUpdate(table)

    fun queryForId(id: Int): ItemEstoqueDTO? {
        return dao.queryForId(id)
    }
}