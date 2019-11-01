package com.lemobs_sigelu.gestao_estoques.common.domain.model.accounts

object DataHolder {

    private var usuario: UsuarioAccounts? = null
    private var ambienteCorreto: Boolean? = null
    private var schemeLauncher: String? = null

    internal val nomeUsuario: String
        get() = usuario?.nome ?: ""

    internal val emailUsuario: String
        get() = usuario?.email ?: ""

    var token: String
        get() = usuario?.token_usuario ?: ""
        set(token) {
            usuario!!.token_usuario = token
        }

    val userID: Int?
        get() = usuario?.usuario_id ?: 0

    internal val fotoUsuario: String?
        get() {
            if (usuario == null) return null
            val caminhoFoto = usuario?.foto
            return caminhoFoto
        }

    fun setUsuario(usuario: UsuarioAccounts?) {
        DataHolder.usuario = usuario
    }

    fun setSchemeLauncher(schemeLauncher: String) {
        DataHolder.schemeLauncher = "$schemeLauncher:"
    }

    fun getSchemeLauncher(): String? {
        return schemeLauncher
    }

    fun carregado(): Boolean {
        return usuario != null
    }

    fun SetAmbienteCorreto(ambienteCorreto: Boolean) {
        DataHolder.ambienteCorreto = ambienteCorreto
    }

    fun IsAmbienteCorreto(): Boolean? {
        return ambienteCorreto
    }
}