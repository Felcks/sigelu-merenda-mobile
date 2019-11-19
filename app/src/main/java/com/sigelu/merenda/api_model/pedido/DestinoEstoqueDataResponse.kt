package com.sigelu.merenda.api_model.pedido


class DestinoEstoqueDataResponse (
    val id: Int,
    val tipoEstoque: TipoEstoqueDataResponse,
    val almoxarifado: AlmoxarifadoDataResponse,
    val nucleo: NucleoDataResponse,
    val obra_direta: ObraDiretaDataResponse
)