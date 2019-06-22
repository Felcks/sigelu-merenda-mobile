package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import com.lemobs_sigelu.gestao_estoques.common.domain.model.*
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.*
import io.reactivex.Observable
import javax.inject.Inject

class CadastraPedidoDestinoController @Inject constructor(val gerenciaCadastroPedidoRepository: GerenciaCadastroPedidoRepository,
                                                          val carregaListaNucleoRepository: CarregaListaNucleoRepository,
                                                          val carregaListaEmpresaRepository: CarregaListaEmpresaRepository,
                                                          val carregaListaObraRepository: CarregaListaObraRepository,
                                                          val carregaListaContratoRepository: CarregaListaContratoRepository)
{
    fun confirmaDestinoDePedido(origem: Local, destino: Local, contrato: ContratoEstoque?): Boolean {
        return gerenciaCadastroPedidoRepository.confirmaPedidoDestino(origem, destino, contrato)
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

    fun carregaListaContrato(): Observable<List<ContratoEstoque>> {
        return carregaListaContratoRepository.carregaListaContratosVigentes()
    }
}