package com.lemobs_sigelu.gestao_estoques.ui.lista_pedidos

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Pedido
import com.lemobs_sigelu.gestao_estoques.common.domain.model.TipoPedido
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Status
import com.lemobs_sigelu.gestao_estoques.databinding.ActivityListaPedidoBinding
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_envio.cadastra_envio_1_informacoes_basicas.CadastraEnvioActivity
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_envio.cadastra_envio_2_seleciona_item.SelecionaItemEnvioActivity
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_1_informacoes_basicas.CadastraPedidoDestinoActivity
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento.cadastra_recebimento_1_seleciona_envio.SelecionaEnvioRecebimentoActivity
import com.lemobs_sigelu.gestao_estoques.ui.pedido.activity.VisualizarPedidoActivity
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_lista_pedido.*
import javax.inject.Inject

class ListaPedidoActivity: AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ListaPedidoViewModelFactory
    var viewModel: ListaPedidoViewModel? = null

    var adapter: ListaPedidoAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_pedido)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ListaPedidoViewModel::class.java)
        viewModel!!.response().observe(this, Observer<Response> { response -> processResponse(response) })
        viewModel!!.carregaListaPedido()

        val binding: ActivityListaPedidoBinding = DataBindingUtil.setContentView(this, R.layout.activity_lista_pedido)
        binding.viewModel = viewModel!!
        binding.executePendingBindings()

        this.iniciarAdapter(listOf())

        /* Float button Cadastra pedido */
        menu_item_cadastrar_pedido.setOnClickListener {
            val intent = Intent(this, CadastraPedidoDestinoActivity::class.java)
            startActivity(intent)
        }
    }

    private fun processResponse(response: Response?) {
        when (response?.status) {
            Status.LOADING -> renderLoadingState()
            Status.SUCCESS -> renderDataState(response.data)
            Status.ERROR -> renderErrorState(response.error)
        }
    }

    private fun renderLoadingState() {}

    private fun renderDataState(result: Any?) {
        if(result is List<*>){
            this.iniciarAdapter(result as List<Pedido>)
        }
    }

    private fun renderErrorState(throwable: Throwable?) {
        Toast.makeText(applicationContext, throwable?.message, Toast.LENGTH_SHORT).show()
    }

    private fun iniciarAdapter(list: List<Pedido>){

        if(this.adapter == null) {
            val layoutManager = LinearLayoutManager(applicationContext)
            layoutManager.orientation = LinearLayoutManager.VERTICAL
            rv_lista.layoutManager = layoutManager

            this.adapter = ListaPedidoAdapter(
                applicationContext,
                list,
                envioOuRecebimentoClickListener,
                visualizarPedidoClickListener
            )
            rv_lista.adapter = adapter
        }
        else{
            this.adapter!!.updateAllItens(list)
        }
    }

    private val visualizarPedidoClickListener = object : OneIntParameterClickListener {
        override fun onClick(id: Int) {

            viewModel!!.armazenaPedidoNoFluxo(id)
            val intent = Intent(applicationContext, VisualizarPedidoActivity::class.java)
            startActivity(intent)
        }
    }

    private val envioOuRecebimentoClickListener = object : OneIntParameterClickListener {
        override fun onClick(id: Int) {

            val pedido = adapter?.getPedidoById(id)
            viewModel!!.armazenaPedidoNoFluxo(id)

            val intent = when(pedido?.getTipoPedido()){
                TipoPedido.MEU_NUCLEO_PARA_OUTRO_NUCLEO -> Intent(applicationContext, CadastraEnvioActivity::class.java)
                TipoPedido.MEU_NUCLEO_PARA_OBRA -> Intent(applicationContext, CadastraEnvioActivity::class.java)
                TipoPedido.FORNECEDOR_PARA_MEU_NUCLEO -> Intent(applicationContext, SelecionaEnvioRecebimentoActivity::class.java)
                TipoPedido.OUTRO_NUCLEO_PARA_MEU_NUCLEO -> Intent(applicationContext, SelecionaEnvioRecebimentoActivity::class.java)
                else -> Intent(applicationContext, SelecionaItemEnvioActivity::class.java)
            }

            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_atualiza, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        return when (item?.itemId) {
            R.id.btn_update -> {
                viewModel!!.carregaListaPedido()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}