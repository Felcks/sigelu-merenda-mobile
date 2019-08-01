package com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento.cadastra_recebimento_2_seleciona_item

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.lemobs_sigelu.gestao_estoques.App
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEnvio
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemPedido
import com.lemobs_sigelu.gestao_estoques.common.domain.model.TwoIntParametersClickListener
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Status
import com.lemobs_sigelu.gestao_estoques.databinding.ActivitySelecionaItemRecebimentoBinding
import com.lemobs_sigelu.gestao_estoques.exceptions.ItemSemQuantidadeDisponivelException
import com.lemobs_sigelu.gestao_estoques.exceptions.ListaVaziaException
import com.lemobs_sigelu.gestao_estoques.exceptions.NenhumItemDisponivelException
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento.CadastraRecebimentoViewModelFactory
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento.cadastra_recebimento_3_cadastra_item.CadastraItemRecebimentoActivity
import com.lemobs_sigelu.gestao_estoques.ui.lista_pedidos.ListaPedidoActivity
import com.lemobs_sigelu.gestao_estoques.ui.lista_pedidos.OneIntParameterClickListener
import com.sigelu.core.lib.DialogUtil
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_seleciona_item_envio.*
import java.lang.Exception
import javax.inject.Inject

class SelecionaItemEnvioRecebimentoActivity: AppCompatActivity(), TwoIntParametersClickListener {

    @Inject
    lateinit var viewModelFactory: CadastraRecebimentoViewModelFactory
    var viewModel: SelecionaItemEnvioRecebimentoViewModel? = null

    private var adapter: ListaItemEnvioSelecionavelSimplesAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seleciona_item_recebimento)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SelecionaItemEnvioRecebimentoViewModel::class.java)
        viewModel!!.response().observe(this, Observer<Response> { response -> processResponse(response) })

        val binding: ActivitySelecionaItemRecebimentoBinding = DataBindingUtil.setContentView(this, R.layout.activity_seleciona_item_recebimento)
        binding.viewModel = viewModel!!
        binding.executePendingBindings()

        ll_layout_anterior.setOnClickListener {
            clicouNoAnterior()
        }

        ll_layout_proximo.setOnClickListener {
            clicouNoProximo()
        }
    }

    override fun onResume() {
        viewModel!!.carregarListaMateriais()
        super.onResume()
    }

    private fun clicouNoProximo(){

        try{
            viewModel!!.confirmaSelecaoItens(this.adapter?.itemsParaAdicao as List<ItemEnvio>, this.adapter?.itemsParaRemocao as List<ItemEnvio>)
        }
        catch(e: Exception){
            Toast.makeText(applicationContext, "Ocorreu algum erro", Toast.LENGTH_SHORT).show()
        }

        val intent = Intent(this, CadastraItemRecebimentoActivity::class.java)
        startActivity(intent)
    }

    private fun clicouNoAnterior(){
        this.onBackPressed()
    }


    override fun onClick(id: Int, pos: Int) {

        try {
            val adicionou = viewModel!!.selecionaItem(id)

            if(adicionou){
                adapter?.adicionaItem(pos)
            }
            else{
                adapter?.removeItem(pos)
            }
        }
        catch (e: ItemSemQuantidadeDisponivelException){
            Snackbar.make(ll_all, e.message.toString(), Snackbar.LENGTH_SHORT).show()
        }
        catch (e: Exception){
            Snackbar.make(ll_all, e.message.toString(), Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun iniciarAdapter(list: List<ItemEnvio>){

        val layoutManager = LinearLayoutManager(applicationContext)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_lista.layoutManager = layoutManager

        this.adapter =
            ListaItemEnvioSelecionavelSimplesAdapter(
                applicationContext,
                list,
                this,
                viewModel!!.getIdItensAdicionados()
            )
        rv_lista.adapter = adapter
    }

    fun processResponse(response: Response?) {
        when (response?.status) {
            Status.LOADING -> renderLoadingState()
            Status.SUCCESS -> renderDataState(response.data)
            Status.ERROR -> renderErrorState(response.error)
        }
    }

    private fun renderLoadingState() {
        ll_loading.visibility = View.VISIBLE
        rv_lista.visibility = View.GONE
    }

    private fun renderErrorState(throwable: Throwable?) {

        ll_loading.visibility = View.GONE
        rv_lista.visibility = View.GONE
        tv_error.visibility = View.VISIBLE
        Snackbar.make(ll_all, "Ocorreu algum erro ao carregar itens.", Snackbar.LENGTH_SHORT).show()
    }

    private fun renderDataState(result: Any?) {

        ll_loading.visibility = View.GONE
        rv_lista.visibility = View.VISIBLE
        if(result is List<*>){
            this.iniciarAdapter(result as List<ItemEnvio>)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if(item?.itemId == android.R.id.home){
            val intent = Intent(applicationContext, ListaPedidoActivity::class.java)
            DialogUtil.buildAlertDialogSimNao(
                this,
                "Cancelar recebimento ",
                "Deseja sair e cancelar o recebimento?",
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
}