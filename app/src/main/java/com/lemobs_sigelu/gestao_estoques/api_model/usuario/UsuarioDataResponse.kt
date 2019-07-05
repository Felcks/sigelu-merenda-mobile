package com.lemobs_sigelu.gestao_estoques.api_model.usuario

import com.lemobs_sigelu.gestao_estoques.api_model.nucleo.NucleoDataResponse

/**
 * Created by felcks on Jul, 2019
 */
class UsuarioDataResponse (val id: Int,
                           val nucleo_id: Int?,
                           val nucleo: NucleoDataResponse?)