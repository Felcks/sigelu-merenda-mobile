package com.lemobs_sigelu.gestao_estoques.ui.visualiza_pedido.lista_situacao_fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lemobs_sigelu.gestao_estoques.App
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.domain.model.SituacaoHistorico
import com.lemobs_sigelu.gestao_estoques.common.domain.model.SituacaoPedido
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Status
import com.lemobs_sigelu.gestao_estoques.databinding.FragmentPedidoSituacoesBinding
import com.lemobs_sigelu.gestao_estoques.ui.visualiza_pedido.VisualizarPedidoViewModel
import com.lemobs_sigelu.gestao_estoques.ui.visualiza_pedido.VisualizarPedidoViewModelFactory
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_pedido_situacoes.*
import javax.inject.Inject

class ListaSituacaoFragment : Fragment() {

    var viewModel: VisualizarPedidoViewModel? = null
    var binding: FragmentPedidoSituacoesBinding? = null

    companion object {
        var solicitouCarregamento = false
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pedido_situacoes, container, false)
        return binding!!.root
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.let {
            this.viewModel = ViewModelProviders.of(it).get(VisualizarPedidoViewModel::class.java)
            this.viewModel!!.responseSituacoes.observe(this, Observer<Response> { response -> processResponse(response) })

            binding!!.viewModel = this.viewModel
            binding!!.executePendingBindings()

            if(!solicitouCarregamento) {
                solicitouCarregamento = true
                this.viewModel!!.carregarSituacoesDePedido()
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
        viewModel!!.loadingSituacoes.set(true)
    }

    private fun renderErrorState(throwable: Throwable?) {
        viewModel!!.loadingSituacoes.set(false)
    }

    private fun renderDataState(result: Any?) {
        viewModel!!.loadingSituacoes.set(false)
        if(result is List<*>){
            this.iniciarAdapter(result as List<SituacaoPedido>)
        }
    }

    private fun iniciarAdapter(list: List<SituacaoPedido>){

        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_lista.layoutManager = layoutManager

        val adapter = ListaSituacaoAdapter(App.instance, list)
        rv_lista.adapter = adapter
    }
}