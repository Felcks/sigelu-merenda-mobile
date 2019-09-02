package com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento_sem_pedido.cadastra_recebimento_sem_pedido_2_seleciona_item

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.content.Intent
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
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
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento_sem_pedido.CadastraRecebimentoSemPedidoViewModelFactory
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento_sem_pedido.cadastra_recebimento_sem_pedido_3_cadastra_item.CadastraItemActivity
import com.lemobs_sigelu.gestao_estoques.ui.lista_pedidos.ListaPedidoActivity
import com.sigelu.core.lib.DialogUtil
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

        val binding: ActivitySelecionaItemRecebimentoSemPedidoBinding = DataBindingUtil.setContentView(this, R.layout.activity_seleciona_item_recebimento_sem_pedido)
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

        val intent = Intent(this, CadastraItemActivity::class.java)
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
                DialogUtil.buildAlertDialogSimNao(
                    this,
                    "Cancelar recebimento",
                    "Deseja cancelar o cadastro de recebimento?",
                    {
                        finish()
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(intent)

                    },
                    {}).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}