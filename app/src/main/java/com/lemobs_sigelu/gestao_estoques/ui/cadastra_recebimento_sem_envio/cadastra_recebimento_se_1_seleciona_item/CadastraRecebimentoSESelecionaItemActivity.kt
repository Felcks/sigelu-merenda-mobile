package com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento_sem_envio.cadastra_recebimento_se_1_seleciona_item

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemPedido
import com.lemobs_sigelu.gestao_estoques.common.domain.model.TwoIntParametersClickListener
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Status
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento_sem_envio.CadastraRecebimentoSemEnvioViewModelFactory
import com.lemobs_sigelu.gestao_estoques.ui.lista_pedidos.ListaPedidoActivity
import com.sigelu.core.lib.DialogUtil
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_seleciona_material_pedido.*
import java.lang.Exception
import javax.inject.Inject

class CadastraRecebimentoSESelecionaItemActivity: AppCompatActivity(), TwoIntParametersClickListener {

    @Inject
    lateinit var viewModelFactory: CadastraRecebimentoSemEnvioViewModelFactory
    var viewModel: CadastraRecebimentoSESelecionaItemViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seleciona_item_recebimento_sem_envio)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CadastraRecebimentoSESelecionaItemViewModel::class.java)
        viewModel!!.response().observe(this, Observer<Response> { response -> processResponse(response) })
        viewModel!!.carregaListaItens()
    }

    fun processResponse(response: Response?) {
        when (response?.status) {
            Status.LOADING -> {}
            Status.SUCCESS -> {
                if(response.data is List<*>){
//                    if(response.data.first() is ItemContrato){
//                        this.iniciarAdapter(response.data as List<ItemContrato>)
//                    }
                }
            }
            Status.ERROR -> {}
        }
    }

    private fun iniciarAdapter(lista: List<ItemPedido>){

//        val layoutManager = LinearLayoutManager(applicationContext)
//        layoutManager.orientation = LinearLayoutManager.VERTICAL
//        rv_lista.layoutManager = layoutManager
//
//        val adapter = ListaItemContratoSelecionavelSimplesAdapter(
//            applicationContext,
//            lista,
//            this
//        )
//        rv_lista.adapter = adapter
    }

    override fun onClick(id: Int, pos: Int) {

//        try{
//            val adicionou = viewModel!!.selecionaItem(id)
//
//            if(adicionou){
//                adapter?.adicionaItem(pos)
//            }
//            else{
//                adapter?.removeItem(pos)
//            }
//        }
//        catch (e: Exception){
//            Toast.makeText(applicationContext, "Ocorreu algum erro", Toast.LENGTH_SHORT).show()
//        }
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