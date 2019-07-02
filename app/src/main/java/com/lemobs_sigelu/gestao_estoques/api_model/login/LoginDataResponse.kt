package com.lemobs_sigelu.gestao_estoques.api_model.login

/**
 * Created by felcks on May, 2019
 */
class LoginDataResponse (val token_usuario: String?,
                         val nome: String?,
                         val permissoes: List<PermissaoDataResponse>?,
                         val tipo: String?,
                         val message: String?)