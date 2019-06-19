package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import com.lemobs_sigelu.gestao_estoques.common.domain.model.Empresa
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Nucleo
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Obra
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Local
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CadastraDestinoParaPedidoRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CarregaListaEmpresaRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CarregaListaNucleoRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CarregaListaObraRepository
import io.reactivex.Observable
import javax.inject.Inject

class CadastraPedidoDestinoController @Inject constructor(val fluxoCadastraPedidoDestinoRepository: CadastraDestinoParaPedidoRepository,
                                                          val carregaListaNucleoRepository: CarregaListaNucleoRepository,
                                                          val carregaListaEmpresaRepository: CarregaListaEmpresaRepository,
                                                          val carregaListaObraRepository: CarregaListaObraRepository)
{
    fun setDestinoPedidoNucleo(){
        fluxoCadastraPedidoDestinoRepository.setDestinoPedidoNucleo()
    }

    fun setDestinoPedidoObra(){
        fluxoCadastraPedidoDestinoRepository.setDestinoPedidoObra()
    }

    fun confirmaDestinoDePedido(origem: Local, destino: Local): Boolean {
        return fluxoCadastraPedidoDestinoRepository.confirmaPedidoDestino(origem, destino)
    }

    fun carregaListaNucleo(): Observable<List<Nucleo>> {
        return carregaListaNucleoRepository.carregaListaNucleo()
    }

    fun carregaListaEmpresa(): Observable<List<Empresa>> {
        return carregaListaEmpresaRepository.carregaListaEmpresa()
    }

    fun carregaListaObra(): Observable<List<Obra>> {
        return carregaListaObraRepository.carregaListaObra()
    }
}