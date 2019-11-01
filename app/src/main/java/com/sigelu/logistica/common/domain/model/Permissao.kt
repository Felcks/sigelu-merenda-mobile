package com.sigelu.logistica.common.domain.model

enum class Permissao(val nome: String,
                     val mensagem: String) {

    LOGIN("app-login-obra", "acesso ao aplicativo"),
    CADASTRAR_DIARIO("app-cadastrar-diario", "cadastrar diário"),
    VISUALIZAR_DIARIO("app-visualizar-diario", "visualizar diário"),
    ALTERAR_SITUACAO_OBRA("app-alterar-situacao-obra", "alterar situação"),
    VISUALIZAR_OBRA("app-visualizar-obra", "visualizar obra"),
    VISUALIZAR_LISTA_OBRA("app-visualizar-lista-obra", "visualizar lista obra"),
    VISUALIZAR_MURAL("app-mural-acompanhamento", "visualizar mural")
}