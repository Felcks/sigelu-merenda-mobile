package com.lemobs_sigelu.gestao_estoques.ui.confirma_materiais_pedido

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Material
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Status
import com.lemobs_sigelu.gestao_estoques.ui.adapters.ListaMaterialCadastradoAdapter
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
        viewModel!!.carregarListaMateriais(applicationContext)

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

    private fun renderLoadingState() {

        Log.i("script2", "Carregando")
    }

    private fun renderDataState(result: Any?) {

        Log.i("script2", "sucesso ao carregar")
        if(result is List<*>){
            this.iniciarAdapter(result)
        }
    }

    private fun renderErrorState(throwable: Throwable?) {

        Log.i("script2", "Erro ao carregar")
    }

    private fun iniciarAdapter(list: List<*>){
        val layoutManager = LinearLayoutManager(applicationContext)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_lista.layoutManager = layoutManager

        val adapter = ListaMaterialCadastradoAdapter(applicationContext, list)
        rv_lista.adapter = adapter
    }

    private fun iniciarToolbar(){
        if(toolbar != null){

            val activity = this
            toolbar.setNavigationIcon(R.drawable.ic_cancel)
            setSupportActionBar(toolbar)
            toolbar.setNavigationOnClickListener {

                DialogUtil.buildAlertDialogSimNao(activity,
                    "Cancelar Pedido",
                    "Deseja cancelar o pedido?",
                    {
                        this.viewModel!!.cancelaPedido(applicationContext)
                        val intent = Intent(this, ListaPedidoActivity::class.java)
                        startActivity(intent)
                        this.finish()
                    },
                    {}).show()
            }
        }
    }
}