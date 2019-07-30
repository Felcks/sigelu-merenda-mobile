package com.lemobs_sigelu.gestao_estoques.ui.lista_pedidos

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.*
import android.widget.Toast
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Pedido
import com.lemobs_sigelu.gestao_estoques.common.domain.model.TipoPedido
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Status
import com.lemobs_sigelu.gestao_estoques.databinding.ActivityListaPedidoBinding
import com.lemobs_sigelu.gestao_estoques.exceptions.ListaVaziaException
import com.lemobs_sigelu.gestao_estoques.extensions_constants.esconderTeclado
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_envio.cadastra_envio_1_informacoes_basicas.CadastraEnvioActivity
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_envio.cadastra_envio_2_seleciona_item.SelecionaItemEnvioActivity
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_0_seleciona_tipo.SelecionaTipoPedidoActivity
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_1_informacoes_basicas.CadastraPedidoDestinoActivity
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento.cadastra_recebimento_1_seleciona_envio.SelecionaEnvioRecebimentoActivity
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento_sem_envio.cadastra_recebimento_se_1_seleciona_item.CadastraRecebimentoSESelecionaItemActivity
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento_sem_pedido.cadastra_recebimento_sem_pedido_1_origem_destino.CadastraInformacoesActivity
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento_sem_pedido.cadastra_recebimento_sem_pedido_2_seleciona_item.SelecionaItemActivity
import com.lemobs_sigelu.gestao_estoques.ui.pedido.activity.VisualizarPedidoActivity
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_lista_pedido.*
import javax.inject.Inject

class ListaPedidoActivity: AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ListaPedidoViewModelFactory
    var viewModel: ListaPedidoViewModel? = null

    var adapter: ListaPedidoAdapter? = null
    /* Pesquisa */
    private var pesquisando = false

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
            val intent = Intent(this, SelecionaTipoPedidoActivity::class.java)
            startActivity(intent)
        }

        menu_item_cadastrar_recebimento_sem_pedido.setOnClickListener {
            val intent = Intent(this, CadastraInformacoesActivity::class.java)
            startActivity(intent)
        }

        this.iniciaCampoBusca()
    }

    private fun iniciaCampoBusca(){

        tv_busca.inputType = InputType.TYPE_CLASS_TEXT

        tv_busca.addTextChangedListener( object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {

                filtraBusca(tv_busca.text.toString())

                if(tv_busca.text.isNotEmpty()){
                    img_busca.setBackgroundResource(R.drawable.ic_cancel_gray)
                }
                else{
                    img_busca.setBackgroundResource(R.drawable.ic_magn_glass)
                }
                pesquisando = tv_busca.text.isNotEmpty()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        img_busca.setOnClickListener {

            if(pesquisando){
                limpaBusca()
            }

            tv_busca.esconderTeclado()
        }

        tv_busca.setOnKeyListener { _, keyCode, event ->
            if ((event?.action == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                tv_busca.esconderTeclado()
            }
            false
        }
    }

    private fun filtraBusca(text: String) {
        adapter?.filtraBusca(text)
    }

    private fun limpaBusca(){
        tv_busca.text.clear()
    }

    private fun processResponse(response: Response?) {
        when (response?.status) {
            Status.LOADING -> renderLoadingState()
            Status.SUCCESS -> renderDataState(response.data)
            Status.ERROR -> renderErrorState(response.error)
        }
    }

    private fun renderLoadingState() {
        viewModel!!.isError.set(false)
    }

    private fun renderDataState(result: Any?) {
        if(result is List<*>){
            this.iniciarAdapter(result as List<Pedido>)
        }
    }

    private fun renderErrorState(throwable: Throwable?) {

        viewModel!!.isError.set(true)
        if(throwable is ListaVaziaException){
            viewModel!!.errorMessage.set("Nenhum item encontrado.")
        }
        else{
            viewModel!!.errorMessage.set(throwable?.message)
        }
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
                TipoPedido.FORNECEDOR_PARA_MEU_NUCLEO -> Intent(applicationContext, CadastraRecebimentoSESelecionaItemActivity::class.java)
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
                limpaBusca()
                viewModel!!.carregaListaPedido()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}