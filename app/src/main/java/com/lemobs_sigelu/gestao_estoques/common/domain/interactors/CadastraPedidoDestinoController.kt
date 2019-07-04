package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import com.lemobs_sigelu.gestao_estoques.common.domain.model.*
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.*
import io.reactivex.Observable
import javax.inject.Inject

class CadastraPedidoDestinoController @Inject constructor(val gerenciaCadastroPedidoRepository: GerenciaCadastroPedidoRepository,
                                                          val nucleoRepository: NucleoRepository,
                                                          val empresaRepository: EmpresaRepository,
                                                          val obraRepository: ObraRepository,
                                                          val contratoRepository: ContratoRepository)
{
    fun confirmaDestinoDePedido(origem: Local, destino: Local, contrato: ContratoEstoque?): Boolean {
        return gerenciaCadastroPedidoRepository.confirmaPedidoDestino(origem, destino, contrato)
    }

    fun carregaListaNucleo(): Observable<List<Nucleo>> {
        return nucleoRepository.carregaListaNucleo()
    }

    fun carregaListaEmpresa(): Observable<List<Empresa>> {
        return empresaRepository.carregaListaEmpresa()
    }

    fun carregaListaObra(): Observable<List<Obra>> {
        return obraRepository.carregaListaObra()
    }

    fun carregaListaContrato(): Observable<List<ContratoEstoque>> {
        return contratoRepository.carregaListaContratosVigentes()
    }
}