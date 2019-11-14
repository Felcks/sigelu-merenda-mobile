package com.sigelu.logistica.common.domain.model

class PermissaoModel {

    companion object{
        val PERMISSAO_CADASTRA_PEDIDO = ""
        val PERMISSAO_CADASTRA_RECEBIMENTO = ""

        //Estoque
        val listarEstoque = "logistica-estoque-listar"
        //Envio
        val incluirEnvio = "logistica-envio-incluir"
        //Recebimento
        val incluirRecebimento = "logistica-recebimento-incluir"
        //Movimentação
        val listarMovimentacao = "logistica-movimentacao-listar"
        val visualizarMovimentacao = "logistica-movimentacao-visualizar"
        //RM
        val listarRM = "logistica-requisicao-listar"
        val visualizarRM = "logistica-requisicao-visualizar"
        val cancelarRM = "logistica-requisicao-cancelar"
        val editarRM = "logistica-requisicao-editar"
        val incluirRM = "logistica-requisicao-incluir"
    }
}