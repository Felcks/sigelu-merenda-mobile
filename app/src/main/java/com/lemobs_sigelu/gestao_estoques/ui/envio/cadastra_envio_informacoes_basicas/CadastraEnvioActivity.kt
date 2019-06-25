package com.lemobs_sigelu.gestao_estoques.ui.envio.cadastra_envio_informacoes_basicas

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.lemobs_sigelu.gestao_estoques.R
import dagger.android.AndroidInjection
import javax.inject.Inject

/**
 * Created by felcks on Jun, 2019
 */
class CadastraEnvioActivity: AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: CadastraEnvioViewModelFactory
    var viewModel: CadastraEnvioViewModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastra_envio)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CadastraEnvioViewModel::class.java)


//        val mainBinding: com.lemobs_sigelu.gestao_estoques.databinding.ActivityCadastraMaterialRecebimentoBinding = DataBindingUtil.setContentView(this, R.layout.activity_cadastra_material_recebimento)
//        mainBinding.viewModel = viewModel!!
//        mainBinding.executePendingBindings()
    }


}