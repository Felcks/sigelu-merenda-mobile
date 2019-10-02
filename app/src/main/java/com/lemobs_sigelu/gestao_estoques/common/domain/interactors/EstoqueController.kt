package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import com.lemobs_sigelu.gestao_estoques.App
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEstoque
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemNucleo
import com.lemobs_sigelu.gestao_estoques.common.domain.model.NucleoQuantidadeDeItemEstoque
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.EstoqueRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.ItemEstoqueRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.ItemNucleoRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.NucleoQuantidadeDeItemEstoqueRepository
import com.lemobs_sigelu.gestao_estoques.utils.AppSharedPreferences
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

        val job = CoroutineScope(Dispatchers.IO).launch {

            try{
                nucleoEstoqueID = estoqueRepository.getEstoqueIDNucleo(nucleoID)
            }
            catch(e: Exception){
                nucleoEstoqueID = 0
                this.cancel()
            }
        }
        while(!job.isCompleted && !job.isCancelled){}

        if(job.isCancelled) return listOf()

        return itemEstoqueRepository.carregaListaItemEstoque3(nucleoEstoqueID) ?: listOf()
    }
}