package com.sigelu.merenda.ui.pedido.lista_envio_fragment

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sigelu.merenda.App
import com.sigelu.merenda.R
import com.sigelu.merenda.common.domain.model.Envio
import com.sigelu.merenda.common.viewmodel.Response
import com.sigelu.merenda.common.viewmodel.Status
import com.sigelu.merenda.databinding.FragmentPedidoEnviosBinding
import com.sigelu.merenda.ui.pedido.activity.VisualizarPedidoViewModel
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
            this.viewModel!!.responseItensRecebimento.observe(this, Observer<Response> { response -> processResponseListaItemRecebimento(response)})

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

        viewModel!!.limparListaEnvio()
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

        if(viewModel!!.quantidadeEnviosCarregando() <= 0){
            viewModel!!.loadingEnvios.set(false)
            this.iniciarAdapter(viewModel!!.envios())
        }
    }

    private fun renderDataStateItens(result: Any?) {

        if(viewModel!!.quantidadeEnviosCarregando() <= 0){

            viewModel!!.loadingEnvios.set(false)
            viewModel!!.setQuantidadeEnviosCarregando(viewModel!!.envios().filter{it.recebimentoID != null}.size)
            for(envio in viewModel!!.envios()){

                if(envio.recebimentoID != null)
                    viewModel!!.carregaListaItemRecebimento(envio)
            }

            if(viewModel!!.quantidadeEnviosCarregando() == 0)
                this.iniciarAdapter(viewModel!!.envios())
        }
    }

    fun processResponseListaItemRecebimento(response: Response?){

        when (response?.status) {
            Status.LOADING -> renderLoadingState()
            Status.SUCCESS -> {

                if(viewModel!!.quantidadeEnviosCarregando() <= 0){

                    viewModel!!.loadingEnvios.set(false)
                    this.iniciarAdapter(viewModel!!.envios())
                }
            }
            Status.ERROR -> renderErrorStateItens(response.error)
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