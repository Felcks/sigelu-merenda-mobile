package com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.confirma_cadastro_pedido

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemContrato
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Status
import com.lemobs_sigelu.gestao_estoques.ui.lista_pedidos.ListaPedidoActivity
import com.sigelu.core.lib.DialogUtil
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_visualiza_materiais_cadastrados.*
import javax.inject.Inject

class ConfirmaCadastroPedidoActivity: AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ConfirmaCadastroPedidoViewModelFactory
    var viewModel: ConfirmaCadastroPedidoViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visualiza_materiais_cadastrados)

        toolbar.title = "Cadastrar Pedido"

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ConfirmaCadastroPedidoViewModel::class.java)
        viewModel!!.response().observe(this, Observer<Response> { response -> processResponse(response) })
        viewModel!!.carregaListaItem()

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
            this.iniciarAdapter(result as List<ItemContrato>)
        }
    }

    private fun renderErrorState(throwable: Throwable?) {}

    private fun iniciarAdapter(list: List<ItemContrato>){
        val layoutManager = LinearLayoutManager(applicationContext)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_lista.layoutManager = layoutManager

        val adapter = ListaItemContratoAdapter(applicationContext, list)
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
            "Cancelar pedido",
            "Deseja cancelar o cadastro do pedido?",
            {
                this.viewModel!!.cancelarPedido()
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

            viewModel!!.enviaPedido()


//            Toast.makeText(applicationContext, "Pedido realizado!!", Toast.LENGTH_SHORT).show()
//            val intent = Intent(this, ListaPedidoActivity::class.java)
//            startActivity(intent)
//            this.finishAffinity()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        this.mostrarDialogCancelamento()
    }
}