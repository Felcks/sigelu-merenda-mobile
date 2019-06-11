package com.lemobs_sigelu.gestao_estoques.ui.confirma_materiais_pedido

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemRecebimento
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Status
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_material_pedido.ListaItemRecebimentoAdapter
import com.lemobs_sigelu.gestao_estoques.ui.lista_pedidos.ListaPedidoActivity
import com.lemobs_sigelu.gestao_estoques.ui.seleciona_materiais_pedido.SelecionaMaterialPedidoActivity
import com.sigelu.core.lib.DialogUtil
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_visualiza_materiais_cadastrados.*
import javax.inject.Inject

class ConfirmaMateriaisPedidoActivity: AppCompatActivity(){

    @Inject
    lateinit var viewModelFactory: ConfirmaMateriaisPedidoViewModelFactory
    var viewModel: ConfirmaMateriaisPedidoViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visualiza_materiais_cadastrados)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ConfirmaMateriaisPedidoViewModel::class.java)
        viewModel!!.response().observe(this, Observer<Response> { response -> processResponse(response) })
        viewModel!!.carregaListaItemRecebimento()

        btn_adicionar_materiais.setOnClickListener {
            val intent = Intent(this, SelecionaMaterialPedidoActivity::class.java)
            startActivity(intent)
        }
        this.iniciarToolbar()
    }

    fun processResponse(response: Response?) {
        when (response?.status) {
            Status.LOADING -> renderLoadingState()
            Status.SUCCESS -> renderDataState(response.data)
            Status.ERROR -> renderErrorState(response.error)
        }
    }

    private fun renderLoadingState() {}

    private fun renderDataState(result: Any?) {

        if(result is List<*>){
            this.iniciarAdapter(result as List<ItemRecebimento>)
        }
    }

    private fun renderErrorState(throwable: Throwable?) {}

    private fun iniciarAdapter(list: List<ItemRecebimento>){
        val layoutManager = LinearLayoutManager(applicationContext)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_lista.layoutManager = layoutManager

        val adapter = ListaItemRecebimentoAdapter(applicationContext, list)
        rv_lista.adapter = adapter
    }

    private fun iniciarToolbar(){
        if(toolbar != null){

            toolbar.setNavigationIcon(R.drawable.ic_cancel)
            setSupportActionBar(toolbar)
            toolbar.setNavigationOnClickListener {
                mostrarDialogCancelamento()
            }
        }
    }

    private fun mostrarDialogCancelamento(){

        DialogUtil.buildAlertDialogSimNao(this,
            "Cancelar recebimento",
            "Deseja cancelar o cadastro de recebimento?",
            {
                this.viewModel!!.cancelaPedido()
                val intent = Intent(this, ListaPedidoActivity::class.java)
                startActivity(intent)
                this.finishAffinity()
            },
            {}).show()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val actionBar : ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        menuInflater.inflate(R.menu.menu_done, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if(item?.itemId == R.id.btn_done){

            viewModel!!.confirmaPedido(applicationContext)
            Toast.makeText(applicationContext, "Pedido realizado!!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, ListaPedidoActivity::class.java)
            startActivity(intent)
            this.finishAffinity()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        this.mostrarDialogCancelamento()
    }
}