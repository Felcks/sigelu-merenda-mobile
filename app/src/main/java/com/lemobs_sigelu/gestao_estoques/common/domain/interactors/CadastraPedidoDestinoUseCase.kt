package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import android.content.Context
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Obra
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CadastraPedidoDestinoRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CarregaListaObraRepository
import io.reactivex.Observable
import javax.inject.Inject

class CadastraPedidoDestinoUseCase @Inject constructor(val carregaListaObraRepository: CarregaListaObraRepository,
                                                       val fluxoCadastraPedidoDestinoRepository: CadastraPedidoDestinoRepository
){

    fun getObras(context: Context): Observable<List<Obra>> {
        return carregaListaObraRepository.getObras()
    }

    fun confirmaDestinoDePedido(context: Context) {
        fluxoCadastraPedidoDestinoRepository.confirmaPedidoDestino(context)
    }

    fun setDestinoPedidoNucleo(){
        fluxoCadastraPedidoDestinoRepository.setDestinoPedidoNucleo()
    }

    fun setDestinoPedidoObra(){
        fluxoCadastraPedidoDestinoRepository.setDestinoPedidoObra()
    }

    fun setObraPedido(obra: Obra){
        fluxoCadastraPedidoDestinoRepository.setObraPedido(obra)
    }
}