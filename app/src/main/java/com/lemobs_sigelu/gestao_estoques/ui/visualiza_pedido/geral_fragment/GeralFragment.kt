package com.lemobs_sigelu.gestao_estoques.ui.visualiza_pedido.geral_fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Pedido
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Status
import com.lemobs_sigelu.gestao_estoques.getDataFormatada
import com.lemobs_sigelu.gestao_estoques.tracoSeVazio
import com.lemobs_sigelu.gestao_estoques.ui.visualiza_pedido.VisualizarPedidoViewModel
import com.lemobs_sigelu.gestao_estoques.ui.visualiza_pedido.VisualizarPedidoViewModelFactory
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_pedido_geral.*
import javax.inject.Inject

class GeralFragment: Fragment() {

    @Inject
    lateinit var viewModelFactory: VisualizarPedidoViewModelFactory
    var viewModel: VisualizarPedidoViewModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_pedido_geral, container, false)
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(VisualizarPedidoViewModel::class.java)
        viewModel!!.response().observe(this, Observer<Response> { response -> processResponse(response) })
        viewModel!!.getPedidoBD()
    }

    fun processResponse(response: Response?) {
        when (response?.status) {
            Status.LOADING -> renderLoadingState()
            Status.SUCCESS -> renderDataState(response.data)
            Status.ERROR -> renderErrorState(response.error)
        }
    }

    private fun renderLoadingState() {}

    private fun renderErrorState(throwable: Throwable?) {}

    private fun renderDataState(result: Any?) {

        if(result is Pedido){
            tv_origem.text = result.origem
            tv_destino.text = result.destino
            tv_data_pedido.text = result.dataPedido?.getDataFormatada()?.tracoSeVazio()
            tv_data_entrega.text = result.dataEntrega?.getDataFormatada()?.tracoSeVazio()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel!!.carregarPedido()
    }
}