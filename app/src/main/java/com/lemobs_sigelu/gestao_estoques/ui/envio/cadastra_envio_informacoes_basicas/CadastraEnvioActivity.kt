package com.lemobs_sigelu.gestao_estoques.ui.envio.cadastra_envio_informacoes_basicas

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import dagger.android.AndroidInjection

/**
 * Created by felcks on Jun, 2019
 */
class CadastraEnvioActivity: AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }
}