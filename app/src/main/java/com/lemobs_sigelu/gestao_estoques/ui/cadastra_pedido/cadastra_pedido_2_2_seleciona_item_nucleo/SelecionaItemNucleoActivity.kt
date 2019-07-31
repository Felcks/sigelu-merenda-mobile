package com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_2_2_seleciona_item_nucleo

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemNucleo
import com.lemobs_sigelu.gestao_estoques.common.domain.model.TwoIntParametersClickListener
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Status
import com.lemobs_sigelu.gestao_estoques.exceptions.NenhumItemDisponivelException
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.CadastraPedidoViewModelFactory
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_3_2_cadastra_item_nucleo.CadastraItemNucleoActivity
import com.lemobs_sigelu.gestao_estoques.ui.lista_pedidos.ListaPedidoActivity
import com.sigelu.core.lib.DialogUtil
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_cadastra_envio.*
import kotlinx.android.synthetic.main.activity_cadastra_envio.ll_all
import kotlinx.android.synthetic.main.activity_cadastra_envio.ll_layout_anterior
import kotlinx.android.synthetic.main.activity_cadastra_envio.ll_layout_proximo
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_seleciona_material_pedido.*
import javax.inject.Inject

class SelecionaItemNucleoActivity: AppCompatActivity(), TwoIntParametersClickListener {

    @Inject
    lateinit var viewModelFactory: CadastraPedidoViewModelFactory
    var viewModel: SelecionaItemNucleoViewModel? = null

    private var adapter: ListaItemNucleoSelecionavelSimplesAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seleciona_material_pedido)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SelecionaItemNucleoViewModel::class.java)
        viewModel!!.response.observe(this, Observer<Response> { response -> processResponse(response) })

        ll_layout_anterior.setOnClickListener {
            clicouNoAnterior()
        }

        ll_layout_proximo.setOnClickListener {
            clicouNoProximo()
        }
    }

    private fun clicouNoProximo(){
        try{
            if(this.adapter == null)
                throw NenhumItemDisponivelException()

            viewModel!!.confirmaSelecaoItens(this.adapter?.itemsParaAdicao as List<ItemNucleo>, this.adapter?.itemsParaRemocao as List<ItemNucleo>)

            val intent = Intent(this, CadastraItemNucleoActivity::class.java)
            startActivity(intent)
        }
        catch(e: Exception){
            Snackbar.make(ll_all, e.message.toString(), Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun clicouNoAnterior(){
        this.onBackPressed()
    }

    override fun onResume() {
        viewModel!!.carregaListaItens()
        super.onResume()
    }

    fun processResponse(response: Response?) {
        when (response?.status) {
            Status.LOADING -> renderLoading()
            Status.SUCCESS -> {

                pgb_carregamento.visibility = View.GONE
                tv_error.visibility = View.GONE
                if(response.data is List<*>){

                    if(response.data[0] is ItemNucleo)
                        renderDataItemContrato(response.data as List<ItemNucleo>)
                }

            }
            Status.ERROR -> {
                renderError(response.error)
            }
        }
    }

    private fun renderLoading() {
        pgb_carregamento.visibility = View.VISIBLE
        viewModel!!.loading.set(true)
    }
    private fun renderError(throwable: Throwable?) {

        pgb_carregamento.visibility = View.GONE
        tv_error.text = "Nenhum material disponível neste núcleo."
        tv_error.visibility = View.VISIBLE
    }

    private fun renderDataItemContrato(list: List<ItemNucleo>) {

        val layoutManager = LinearLayoutManager(applicationContext)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_lista.layoutManager = layoutManager

        this.adapter = ListaItemNucleoSelecionavelSimplesAdapter(applicationContext,
            list,
            this,
            viewModel!!.getItensAdicionadosNucleo())
        rv_lista.adapter = adapter
    }

    override fun onClick(id: Int, pos: Int) {

        try{
            val adicionou = viewModel!!.selecionaItem(id)

            if(adicionou){
                adapter?.adicionaItem(pos)
            }
            else{
                adapter?.removeItem(pos)
            }
        }
        catch (e: Exception){
            Toast.makeText(applicationContext, "Ocorreu algum erro", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if(item?.itemId == android.R.id.home){
            val intent = Intent(applicationContext, ListaPedidoActivity::class.java)
            DialogUtil.buildAlertDialogSimNao(
                this,
                "Cancelar pedido ",
                "Deseja sair e cancelar o pedido?",
                {
                    finish()
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                },
                {}).show()

            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val actionBar : ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_cancel)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}