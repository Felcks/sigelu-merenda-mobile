package com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento_sem_pedido.cadastra_recebimento_sem_pedido_1_origem_destino

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Fornecedor
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Nucleo
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Status
import com.lemobs_sigelu.gestao_estoques.exceptions.UsuarioSemNucleoException
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento_sem_pedido.CadastraRecebimentoSemPedidoViewModelFactory
import com.lemobs_sigelu.gestao_estoques.utils.CustomAdapterTuple
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_cadastra_recebimento_sp_informacoes.*
import javax.inject.Inject

/**
 * Created by felcks on Jul, 2019
 */
class CadastraInformacoesActivity: AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: CadastraRecebimentoSemPedidoViewModelFactory
    var viewModel: CadastraInformacoesViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastra_recebimento_sp_informacoes)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CadastraInformacoesViewModel::class.java)
        viewModel!!.response().observe(this, Observer<Response> { response -> processResponse(response) })
        this.carregarInformacoes()

        val binding: com.lemobs_sigelu.gestao_estoques.databinding.ActivityCadastraRecebimentoSpInformacoesBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_cadastra_recebimento_sp_informacoes)
        binding.viewModel = viewModel!!
        binding.executePendingBindings()
    }

    fun processResponse(response: Response?) {
        when (response?.status) {
            Status.LOADING -> {}
            Status.SUCCESS -> {

                if(response.data is List<*>){
                    if(response.data.firstOrNull() is Fornecedor){
                        this.iniciaSpinnerOrigem(response.data as List<Fornecedor>)
                    }
                }
            }
            Status.ERROR -> {

            }
        }
    }

    fun carregarInformacoes(){

        try{
            viewModel!!.carregaMeuNucleo()
            viewModel!!.carregaListaFornecedores()
        }
        catch(e: UsuarioSemNucleoException){

            //mostrar snackbar de erro
            Log.i("script2", "uaheufhaf")
        }
    }

    private fun iniciaSpinnerOrigem(lista: List<Fornecedor>){

        val textoDestino = lista.map { CustomAdapterTuple.RowItem("Fornecedor", it.nome ?: "") }
        var adapter = CustomAdapterTuple(this, textoDestino)
        spinner_origem.adapter = adapter

        spinner_origem.onItemSelectedListener = viewModel!!.selecionadorOrigem
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if(item?.itemId == R.id.btn_done) {

        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val actionBar : ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        menuInflater.inflate(R.menu.menu_done, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}