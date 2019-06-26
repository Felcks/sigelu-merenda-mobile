package com.lemobs_sigelu.gestao_estoques.api_model.cadastra_envio

/**
 * Created by felcks on Jun, 2019
 */
class EnvioDataRequest (val motorista: String,
                        val hora_saida: String,
                        val data_saida: String,
                        val items: List<ItemEnvioDataRequest>)