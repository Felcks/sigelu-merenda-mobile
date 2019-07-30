package com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento_sem_pedido.cadastra_recebimento_sem_pedido_2_seleciona_item

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEstoque
import com.lemobs_sigelu.gestao_estoques.common.domain.model.TwoIntParametersClickListener
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Status
import com.lemobs_sigelu.gestao_estoques.databinding.ActivitySelecionaItemRecebimentoSemPedidoBinding
import com.lemobs_sigelu.gestao_estoques.exceptions.ItemSemQuantidadeDisponivelException
import com.lemobs_sigelu.gestao_estoques.exceptions.ListaVaziaException
import com.lemobs_sigelu.gestao_estoques.exceptions.NenhumItemDisponivelException
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento.cadastra_recebimento_3_cadastra_item.CadastraItemRecebimentoActivity
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento_sem_pedido.CadastraRecebimentoSemPedidoViewModelFactory
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_seleciona_item_recebimento_sem_pedido.*
import javax.inject.Inject

/**
 * Created by felcks on Jul, 2019
 */
class SelecionaItemActivity: AppCompatActivity(), TwoIntParametersClickListener {

    @Inject
    lateinit var viewModelFactory: CadastraRecebimentoSemPedidoViewModelFactory
    var viewModel: SelecionaItemViewModel? = null

    private var adapter: ListaItemContratoSelecionavelSimplesAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seleciona_item_recebimento_sem_pedido)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SelecionaItemViewModel::class.java)
        viewModel!!.response().observe(this, Observer<Response> { response -> processResponse(response) })
        viewModel!!.carregaListaItens()

        val binding: ActivitySelecionaItemRecebimentoSemPedidoBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_seleciona_item_recebimento_sem_pedido)
        binding.viewModel = viewModel!!
        binding.executePendingBindings()

        ll_layout_anterior.setOnClickListener {
            clicouNoAnterior()
        }

        ll_layout_proximo.setOnClickListener {
            clicouNoProximo()
        }

    }

    private fun clicouNoProximo(){

        try{
            viewModel!!.confirmaSelecaoItens(this.adapter?.itensParaAdicao as List<ItemEstoque>, this.adapter?.itensParaRemocao as List<ItemEstoque>)
        }
        catch(e: java.lang.Exception){
            Toast.makeText(applicationContext, "Ocorreu algum erro", Toast.LENGTH_SHORT).show()
        }

        val intent = Intent(this, CadastraItemRecebimentoActivity::class.java)
        startActivity(intent)
    }

    private fun clicouNoAnterior(){
        this.onBackPressed()
    }

    override fun onClick(id: Int, pos:Int) {

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

    private fun iniciarAdapter(lista: List<ItemEstoque>){

        val layoutManager = LinearLayoutManager(applicationContext)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_lista.layoutManager = layoutManager

        this.adapter =
            ListaItemContratoSelecionavelSimplesAdapter(
            applicationContext,
            lista,
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

    private fun renderLoadingState() {}

    private fun renderErrorState(throwable: Throwable?) {

        if(throwable is ListaVaziaException || throwable is NenhumItemDisponivelException){
            (ll_erro as TextView).text = "Todos os materiais foram cadastrados."
            ll_erro.visibility = View.VISIBLE
        }
        else{
            Snackbar.make(ll_all, "Ocorreu algum erro ao carregar itens.", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun renderDataState(result: Any?) {

        ll_erro.visibility = View.GONE
        if(result is List<*>){
            this.iniciarAdapter(result as List<ItemEstoque>)
        }
    }
}