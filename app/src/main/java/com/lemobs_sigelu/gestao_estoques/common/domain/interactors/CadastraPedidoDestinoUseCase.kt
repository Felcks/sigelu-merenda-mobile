package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import android.content.Context
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Obra
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CadastraDestinoParaPedidoRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CarregaListaObraRepository
import io.reactivex.Observable
import javax.inject.Inject

class CadastraPedidoDestinoUseCase @Inject constructor(val carregaListaObraRepository: CarregaListaObraRepository,
                                                       val fluxoCadastraPedidoDestinoRepository: CadastraDestinoParaPedidoRepository
){

    fun getObras(context: Context): Observable<List<Obra>> {
        return carregaListaObraRepository.getObras()
    }

    fun setDestinoPedidoNucleo(){
        fluxoCadastraPedidoDestinoRepository.setDestinoPedidoNucleo()
    }

    fun setDestinoPedidoObra(){
        fluxoCadastraPedidoDestinoRepository.setDestinoPedidoObra()
    }

    fun confirmaDestinoDePedido(context: Context, obraSelecionadaId: Int): Boolean {
        return fluxoCadastraPedidoDestinoRepository.confirmaPedidoDestino(context, obraSelecionadaId)
    }
}