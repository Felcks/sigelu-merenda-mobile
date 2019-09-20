package com.lemobs_sigelu.gestao_estoques.api_model.pedido

class OrigemEstoqueDataResponse(
    val id: Int,
    val tipo_estoque: TipoEstoqueDataResponse,
    val almoxarifado: AlmoxarifadoDataResponse,
    val nucleo: NucleoDataResponse
)