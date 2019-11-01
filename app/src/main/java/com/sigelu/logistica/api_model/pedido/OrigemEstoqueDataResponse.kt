package com.sigelu.logistica.api_model.pedido

class OrigemEstoqueDataResponse(
    val id: Int,
    val tipo_estoque: TipoEstoqueDataResponse,
    val almoxarifado: AlmoxarifadoDataResponse,
    val nucleo: NucleoDataResponse
)