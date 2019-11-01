package com.sigelu.logistica.api_model.pedido

class EstoqueDataResponse(val id: Int,
                          val tipo_estoque: TipoEstoqueDataResponse,
                          val nucleo: NucleoDataResponse?,
                          val obra_direta: ObraDiretaDataResponse?,
                          val almoxarifado: AlmoxarifadoDataResponse?)