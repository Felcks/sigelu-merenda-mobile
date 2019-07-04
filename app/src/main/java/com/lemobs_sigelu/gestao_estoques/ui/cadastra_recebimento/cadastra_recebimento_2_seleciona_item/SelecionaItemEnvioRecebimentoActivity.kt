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
import android.view.View
import android.widget.TextView
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEnvio
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Status
import com.lemobs_sigelu.gestao_estoques.databinding.ActivitySelecionaItemRecebimentoBinding
import com.lemobs_sigelu.gestao_estoques.exceptions.ItemSemQuantidadeDisponivelException
import com.lemobs_sigelu.gestao_estoques.exceptions.ListaVaziaException
import com.lemobs_sigelu.gestao_estoques.exceptions.NenhumItemDisponivelException
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento.CadastraRecebimentoViewModelFactory
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento.cadastra_recebimento_3_cadastra_item.CadastraItemRecebimentoActivity
import com.lemobs_sigelu.gestao_estoques.ui.lista_pedidos.OneIntParameterClickListener
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_seleciona_item_envio.*
import java.lang.Exception
import javax.inject.Inject

class SelecionaItemEnvioRecebimentoActivity: AppCompatActivity(), OneIntParameterClickListener {

    @Inject
    lateinit var viewModelFactory: CadastraRecebimentoViewModelFactory
    var viewModel: SelecionaItemEnvioRecebimentoViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seleciona_item_recebimento)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SelecionaItemEnvioRecebimentoViewModel::class.java)
        viewModel!!.response().observe(this, Observer<Response> { response -> processResponse(response) })
        viewModel!!.carregarListaMateriais()

        val binding: ActivitySelecionaItemRecebimentoBinding = DataBindingUtil.setContentView(this, R.layout.activity_seleciona_item_recebimento)
        binding.viewModel = viewModel!!
        binding.executePendingBindings()
    }


    override fun onClick(id: Int) {

        try {
            viewModel!!.selecionaItem(id)
            val intent = Intent(this, CadastraItemRecebimentoActivity::class.java)
            startActivity(intent)
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

        val adapter =
            ListaItemEnvioSelecionavelSimplesAdapter(
                applicationContext,
                list,
                this
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
            this.iniciarAdapter(result as List<ItemEnvio>)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val actionBar : ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}