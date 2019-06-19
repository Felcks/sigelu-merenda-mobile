package com.lemobs_sigelu.gestao_estoques.ui.pedido.visualiza_pedido.geral_fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.lemobs_sigelu.gestao_estoques.App
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Pedido
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Status
import com.lemobs_sigelu.gestao_estoques.databinding.FragmentPedidoGeralBinding
import com.lemobs_sigelu.gestao_estoques.getDataFormatada
import com.lemobs_sigelu.gestao_estoques.tracoSeVazio
import com.lemobs_sigelu.gestao_estoques.ui.pedido.visualiza_pedido.VisualizarPedidoViewModel
import kotlinx.android.synthetic.main.fragment_pedido_geral.*

class GeralFragment: Fragment() {

    var viewModel: VisualizarPedidoViewModel? = null
    var binding: FragmentPedidoGeralBinding? = null

    companion object {
        var solicitouCarregamento = false
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pedido_geral, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.let {
            this.viewModel = ViewModelProviders.of(it).get(VisualizarPedidoViewModel::class.java)
            this.viewModel!!.response().observe(this, Observer<Response> { response -> processResponse(response) })

            binding?.viewModel = this.viewModel
            binding?.executePendingBindings()

            if(!solicitouCarregamento) {
                solicitouCarregamento = true
                this.viewModel!!.carregarPedido()
            }
        }
    }

    fun processResponse(response: Response?) {
        when (response?.status) {
            Status.LOADING -> renderLoadingState()
            Status.SUCCESS -> renderDataState(response.data)
            Status.ERROR -> renderErrorState(response.error)
        }
    }

    private fun renderLoadingState() {
        viewModel!!.loading.set(true)
    }

    private fun renderErrorState(throwable: Throwable?) {
        viewModel!!.loading.set(false)
        Toast.makeText(App.instance, throwable?.message, Toast.LENGTH_SHORT).show()
    }

    private fun renderDataState(result: Any?) {

        if(result is Pedido){
            tv_origem.text = result.getOrigemFormatado()
            tv_destino.text = result.getDestinoFormatado()
            tv_data_pedido.text = result.dataPedido?.getDataFormatada()?.tracoSeVazio() ?: "-"
            tv_data_entrega.text = result.dataEntrega?.getDataFormatada()?.tracoSeVazio() ?: "-"
        }

        viewModel!!.loading.set(false)
    }

    override fun onResume() {
        super.onResume()
        viewModel!!.carregarPedido()
    }
}