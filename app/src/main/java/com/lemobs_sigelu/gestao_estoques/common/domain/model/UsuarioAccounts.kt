package com.lemobs_sigelu.gestao_estoques.common.domain.model

import java.util.ArrayList

class UsuarioAccounts {

    val usuario_id: Int? = null
    var token_usuario: String? = null
    val matricula: String? = null
    val nome: String? = null
    val email: String? = null
    val assinatura: String? = null
    val foto: String? = null
    private val setores: ArrayList<Int>? = null

    val setor_id: Int?
        get() = setores!![0]
}
