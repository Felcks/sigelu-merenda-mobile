package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import android.content.Context
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemRecebimento
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CarregaListaItemRecebimentoRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CadastraRecebimentoRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.GerenciaRecebimentoRepository
import io.reactivex.Observable
import javax.inject.Inject

class ConfirmaMateriaisRecebimentoController @Inject constructor(val carregaListaItemRecebimentoRepository: CarregaListaItemRecebimentoRepository,
                                                                 val cadastraRecebimentoRepository: CadastraRecebimentoRepository,
                                                                 val gerenciaRecebimentoRepository: GerenciaRecebimentoRepository) {

    fun carregaListaItemRecebimento(): Observable<List<ItemRecebimento>> {
        return carregaListaItemRecebimentoRepository.getListaItemRecebimento()
    }

    fun enviaRecebimento(): Observable<Boolean>{
        return cadastraRecebimentoRepository.enviaRecebimento()
    }

    fun cancelaRecebimento(){
        gerenciaRecebimentoRepository.apagarListaItemRecebimentoAnteriores()
    }
}