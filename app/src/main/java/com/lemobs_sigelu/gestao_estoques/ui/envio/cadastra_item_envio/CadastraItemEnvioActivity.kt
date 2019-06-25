package com.lemobs_sigelu.gestao_estoques.ui.envio.cadastra_item_envio

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEnvio
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Status
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_cadastra_item_envio.*
import javax.inject.Inject

/**
 * Created by felcks on Jun, 2019
 */
class CadastraItemEnvioActivity: AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: CadastraItemEnvioViewModelFactory
    var viewModel: CadastraItemEnvioViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastra_item_envio)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CadastraItemEnvioViewModel::class.java)
        viewModel!!.response().observe(this, Observer<Response> { response -> processResponse(response) })
    }

    private fun processResponse(response: Response?) {
        when (response?.status) {
            Status.LOADING -> renderLoadingState()
            Status.SUCCESS -> {

                if(response.data is List<*>){

                    if(response.data.first() is ItemEnvio) {
                        this.iniciarPreenchimento((response.data as ItemEnvio?))
                    }
                }
            }
            Status.ERROR -> renderErrorState(response.error)
        }
    }

    private fun renderLoadingState() {
        viewModel!!.loading.set(true)
    }


    private fun renderErrorState(throwable: Throwable?) {
        viewModel!!.loading.set(false)
    }

    private fun iniciarPreenchimento(itemEnvio: ItemEnvio?){

        if(itemEnvio != null){
            if(itemEnvio != null) {
                tv_1.text = itemEnvio.itemEstoque?.nomeAlternativo
                tv_2.text = itemEnvio.itemEstoque?.descricao
                tv_3.text = itemEnvio.itemEstoque?.unidadeMedida?.getNomeESiglaPorExtenso()
                tv_4.setText(itemEnvio.quantidadeUnidade.toString())
            }
        }


    }
}