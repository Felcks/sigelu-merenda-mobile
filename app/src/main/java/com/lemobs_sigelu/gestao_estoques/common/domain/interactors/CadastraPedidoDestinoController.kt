package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import android.content.Context
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Empresa
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Nucleo
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Obra
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CadastraDestinoParaPedidoRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CarregaListaEmpresaRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CarregaListaNucleoRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CarregaListaObraRepository
import io.reactivex.Observable
import javax.inject.Inject

class CadastraPedidoDestinoController @Inject constructor(val fluxoCadastraPedidoDestinoRepository: CadastraDestinoParaPedidoRepository,
                                                          val carregaListaNucleoRepository: CarregaListaNucleoRepository,
                                                          val carregaListaEmpresaRepository: CarregaListaEmpresaRepository)
{
    fun setDestinoPedidoNucleo(){
        fluxoCadastraPedidoDestinoRepository.setDestinoPedidoNucleo()
    }

    fun setDestinoPedidoObra(){
        fluxoCadastraPedidoDestinoRepository.setDestinoPedidoObra()
    }

    fun confirmaDestinoDePedido(context: Context, obraSelecionadaId: Int): Boolean {
        return fluxoCadastraPedidoDestinoRepository.confirmaPedidoDestino(context, obraSelecionadaId)
    }

    fun carregaListaNucleo(): Observable<List<Nucleo>> {
        return carregaListaNucleoRepository.carregaListaNucleo()
    }

    fun carregaListaEmpresa(): Observable<List<Empresa>> {
        return carregaListaEmpresaRepository.carregaListaEmpresa()
    }
}