package com.lemobs_sigelu.gestao_estoques.api_model.pedido

class EstoqueDataResponse(val id: Int,
                          val tipo_estoque: TipoEstoqueDataResponse,
                          val nucleo: NucleoDataResponse?,
                          val obra_direta: ObraDiretaDataResponse?,
                          val almoxarifado: AlmoxarifadoDataResponse?)