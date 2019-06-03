package com.lemobs_sigelu.gestao_estoques.ui.visualiza_pedido.lista_material_fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemPedido
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Status
import com.lemobs_sigelu.gestao_estoques.databinding.FragmentPedidoMateriaisBinding
import com.lemobs_sigelu.gestao_estoques.ui.visualiza_pedido.VisualizarPedidoViewModel
import kotlinx.android.synthetic.main.fragment_pedido_materiais.*

class ListaMaterialFragment : Fragment() {

    var viewModel: VisualizarPedidoViewModel? = null
    var binding: FragmentPedidoMateriaisBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pedido_materiais, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.let {
            this.viewModel = ViewModelProviders.of(it).get(VisualizarPedidoViewModel::class.java)
            this.viewModel!!.responseMateriais.observe(this, Observer<Response> { response -> processResponse(response) })

            binding!!.viewModel = this.viewModel
            binding!!.executePendingBindings()
            this.viewModel!!.carregarMateriaisDePedido()
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

    private fun renderLoadingState() {}

    private fun renderErrorState(throwable: Throwable?) {}

    private fun renderDataState(result: Any?) {

        if(result is List<*>){
            this.iniciarAdapter(result as List<ItemPedido>)
        }
    }

    private fun iniciarAdapter(list: List<ItemPedido>){

        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_lista.layoutManager = layoutManager

        val adapter = ListaMaterialAdapter(context!!, list)
        rv_lista.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
    }
}