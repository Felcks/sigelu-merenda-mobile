package com.lemobs_sigelu.gestao_estoques.ui.cadastra_envio.cadastra_envio_2_seleciona_item

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.IntentCompat
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemPedido
import com.lemobs_sigelu.gestao_estoques.common.domain.model.TwoIntParametersClickListener
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Status
import com.lemobs_sigelu.gestao_estoques.exceptions.ListaVaziaException
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_envio.CadastraEnvioViewModelFactory
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_envio.cadastra_envio_1_informacoes_basicas.CadastraEnvioActivity
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_envio.cadastra_envio_3_cadastra_item.CadastraItemEnvioActivity
import com.lemobs_sigelu.gestao_estoques.ui.lista_pedidos.ListaPedidoActivity
import com.lemobs_sigelu.gestao_estoques.ui.lista_pedidos.OneIntParameterClickListener
import com.lemobs_sigelu.gestao_estoques.ui.pedido.activity.VisualizarPedidoActivity
import com.sigelu.core.lib.DialogUtil.Companion.buildAlertDialogSimNao
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_seleciona_item_envio.*
import kotlinx.android.synthetic.main.activity_seleciona_item_envio.ll_all
import kotlinx.android.synthetic.main.activity_seleciona_item_envio.ll_layout_anterior
import kotlinx.android.synthetic.main.activity_seleciona_item_envio.ll_layout_proximo
import java.lang.Exception
import javax.inject.Inject



/**
 * Created by felcks on Jun, 2019
 */
class SelecionaItemEnvioActivity: AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: CadastraEnvioViewModelFactory
    var viewModel: SelecionaItemEnvioViewModel? = null

    private var adapter: ListaItemSelecionavelSimplesAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(com.lemobs_sigelu.gestao_estoques.R.layout.activity_seleciona_item_envio)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SelecionaItemEnvioViewModel::class.java)
        viewModel!!.response().observe(this, Observer<Response> { response -> processResponse(response) })

        val mainBinding: com.lemobs_sigelu.gestao_estoques.databinding.ActivitySelecionaItemEnvioBinding = DataBindingUtil.setContentView(this, com.lemobs_sigelu.gestao_estoques.R.layout.activity_seleciona_item_envio)
        mainBinding.viewModel = viewModel!!
        mainBinding.executePendingBindings()

        ll_layout_proximo.setOnClickListener {
            this.clicouProximo()
        }

        ll_layout_anterior.setOnClickListener {
            this.clicouAnterior()
        }
    }

    override fun onResume() {
        viewModel!!.carregaListaItens()
        super.onResume()
    }

    private fun processResponse(response: Response?) {
        when (response?.status) {
            Status.LOADING -> renderLoadingState()
            Status.SUCCESS -> {

                if(response.data is List<*>){

                    if(response.data.first() is ItemPedido)
                        iniciarAdapter(response.data as List<ItemPedido>)
                }
            }
            Status.ERROR -> renderErrorState(response.error)
        }
    }

    private fun renderLoadingState() {}

    private fun iniciarAdapter(list: List<ItemPedido>) {

        val layoutManager = LinearLayoutManager(applicationContext)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_lista.layoutManager = layoutManager

        this.adapter = ListaItemSelecionavelSimplesAdapter(
            applicationContext,
            list,
            selecionaItemPedidoClickListener,
            viewModel?.getIdItensAdicionados() ?: listOf()
        )

        rv_lista.adapter = adapter
    }

    private fun renderErrorState(throwable: Throwable?) {

        Snackbar.make(ll_all, "Ocorreu algum erro ao carregar materiais.", Snackbar.LENGTH_SHORT).show()
    }

    private val selecionaItemPedidoClickListener = object: TwoIntParametersClickListener{

        override fun onClick(id: Int, position: Int) {

            try {
                //true adicionou, false removeu
                val adicionou = viewModel!!.selecionaItem(id)

                if(adicionou){
                    adapter?.adicionarItem(position)
                }
                else{
                    adapter?.removerItem(position)
                }
            }
            catch (e: Exception){
                Toast.makeText(applicationContext, "Ocorreu algum erro", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun clicouProximo(){

        try{

            viewModel!!.confirmaSelecaoItens(this.adapter?.itemsParaAdicao as List<ItemPedido>, this.adapter?.itemsParaRemocao as List<ItemPedido>)

            val intent = Intent(applicationContext, CadastraItemEnvioActivity::class.java)
            startActivity(intent)
        }
        catch(e: Exception){
            Toast.makeText(applicationContext, "Ocorreu algum erro", Toast.LENGTH_SHORT).show()
        }
    }

    private fun clicouAnterior(){
        onBackPressed()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val actionBar : ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_cancel)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                val intent = Intent(applicationContext, ListaPedidoActivity::class.java)
                buildAlertDialogSimNao(
                    this,
                    "Cancelar envio ",
                    "Deseja sair e cancelar o envio?",
                    {
                        super.onBackPressed()
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                        startActivity(intent)

                    },
                    {}).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}