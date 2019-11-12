package com.sigelu.logistica.common.domain.interactors

import com.sigelu.logistica.App
import com.sigelu.logistica.common.domain.model.ItemEstoque
import com.sigelu.logistica.common.domain.model.NucleoQuantidadeDeItemEstoque
import com.sigelu.logistica.common.domain.repository.EstoqueRepository
import com.sigelu.logistica.common.domain.repository.ItemEstoqueRepository
import com.sigelu.logistica.common.domain.repository.ItemNucleoRepository
import com.sigelu.logistica.common.domain.repository.NucleoQuantidadeDeItemEstoqueRepository
import com.sigelu.logistica.utils.AppSharedPreferences
import io.reactivex.Observable
import kotlinx.coroutines.*
import java.lang.Exception
import javax.inject.Inject

class EstoqueController @Inject constructor(private val itemEstoqueRepository: ItemEstoqueRepository,
                                            private val itemNucleoEstoqueRepository: ItemNucleoRepository,
                                            private val estoqueRepository: EstoqueRepository,
                                            private val nucleoQuantidadeDeItemEstoqueRepository: NucleoQuantidadeDeItemEstoqueRepository){

    fun getListaItemEstoque(): Observable<List<ItemEstoque>>{

        val nucleoID = AppSharedPreferences.getNucleoID(App.instance)
        return itemNucleoEstoqueRepository.getListaItemEstoqueDeNucleo(nucleoID)
    }

    fun getListaNucleoQuantidade(itemEstoqueID: Int): Observable<List<NucleoQuantidadeDeItemEstoque>>{

        return nucleoQuantidadeDeItemEstoqueRepository.carregaListaNucleoQuantidade(itemEstoqueID)
    }

    suspend fun getListaItemEstoque3(): List<ItemEstoque>{
        val nucleoID = AppSharedPreferences.getNucleoID(App.instance)
        var nucleoEstoqueID: Int = 0

        try{
            nucleoEstoqueID = estoqueRepository.getEstoqueIDNucleo(nucleoID)
        }
        catch(e: Exception){
            throw Exception("Lista de estoque não carregada.")
        }

        return itemEstoqueRepository.carregaListaItemEstoque3(nucleoEstoqueID) ?: listOf()
    }
}