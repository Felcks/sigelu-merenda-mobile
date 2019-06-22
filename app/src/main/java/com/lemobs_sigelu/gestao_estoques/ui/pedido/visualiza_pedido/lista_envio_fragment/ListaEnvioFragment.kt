package com.lemobs_sigelu.gestao_estoques.ui.pedido.visualiza_pedido.lista_envio_fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lemobs_sigelu.gestao_estoques.App
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Envio
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Status
import com.lemobs_sigelu.gestao_estoques.databinding.FragmentPedidoEnviosBinding
import com.lemobs_sigelu.gestao_estoques.ui.pedido.visualiza_pedido.VisualizarPedidoViewModel
import kotlinx.android.synthetic.main.fragment_pedido_envios.*

/**
 * Created by felcks on Jun, 2019
 */
class ListaEnvioFragment : Fragment() {

    var viewModel: VisualizarPedidoViewModel? = null
    var binding: FragmentPedidoEnviosBinding? = null

    companion object {
        var solicitouCarregamento = false
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pedido_envios, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.let {
            this.viewModel = ViewModelProviders.of(it).get(VisualizarPedidoViewModel::class.java)
            this.viewModel!!.responseEnvios.observe(this, Observer<Response> { response -> processResponse(response) })
            this.viewModel!!.responseItensEnvios.observe(this, Observer<Response> { response -> processResponseEnvioCompleto(response)})

            binding!!.viewModel = this.viewModel
            binding!!.executePendingBindings()

            if(!solicitouCarregamento) {
                solicitouCarregamento = true
                this.viewModel!!.carregaEnviosDePedido()
            }
        }

        this.iniciarAdapter(listOf())
    }

    fun processResponse(response: Response?) {
        when (response?.status) {
            Status.LOADING -> renderLoadingState()
            Status.SUCCESS -> renderDataState(response.data)
            Status.ERROR -> renderErrorState(response.error)
        }
    }

    private fun renderLoadingState() {
        viewModel!!.loadingEnvios.set(true)
    }

    private fun renderErrorState(throwable: Throwable?) {
        viewModel!!.loadingEnvios.set(false)
        viewModel!!.errorEnvios.set(true)
    }

    private fun renderDataState(result: Any?) {

        if(result is List<*>){
            for(envio in result as List<Envio>){
                viewModel!!.carregarItensDeEnvio(envio)
            }
        }
    }

    fun processResponseEnvioCompleto(response: Response?){

        when (response?.status) {
            Status.LOADING -> renderLoadingState()
            Status.SUCCESS -> renderDataStateItens(response.data)
            Status.ERROR -> renderErrorStateItens(response.error)
        }
    }

    private fun renderErrorStateItens(throwable: Throwable?) {

        viewModel!!.loadingEnvios.set(false)
        if(viewModel!!.quantidadeEnviosCarregando() <= 0){
            this.iniciarAdapter(viewModel!!.envios())
        }
    }

    private fun renderDataStateItens(result: Any?) {

        viewModel!!.loadingEnvios.set(false)
        if(viewModel!!.quantidadeEnviosCarregando() <= 0){
            this.iniciarAdapter(viewModel!!.envios())
        }
    }

    private fun iniciarAdapter(list: List<Envio>){

        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_lista.layoutManager = layoutManager

        val adapter = ListaEnvioAdapter(App.instance, list)
        rv_lista.adapter = adapter
    }
}