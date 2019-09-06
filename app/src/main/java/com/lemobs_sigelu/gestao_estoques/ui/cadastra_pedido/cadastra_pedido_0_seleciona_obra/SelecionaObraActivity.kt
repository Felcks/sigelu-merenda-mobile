package com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_0_seleciona_obra

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.lemobs_sigelu.gestao_estoques.App
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ActivityDeFluxo
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Local
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Obra
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Status
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_1_3_obra.CadastraObraViewModel
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_1_seleciona_item.SelecionaItemPedidoParaNucleoActivity
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_2_2_seleciona_item_nucleo.SelecionaItemNucleoActivity
import com.lemobs_sigelu.gestao_estoques.ui.lista_pedidos.ListaPedidoActivity
import com.lemobs_sigelu.gestao_estoques.utils.AppSharedPreferences
import com.sigelu.core.lib.DialogUtil
import kotlinx.android.synthetic.main.activity_cadastra_pedido_obra.*
import org.koin.android.ext.android.inject
import java.lang.Exception

class SelecionaObraActivity: AppCompatActivity(), ActivityDeFluxo {

    val viewModel: SelecionaObraViewModel by inject()

    private var listaObra = mutableListOf<Obra>()
    private var obraSelecionada: Int? =  null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastra_pedido_obra)

        viewModel.listaObra.observe(this, Observer<Response> { response -> processResponse(response) })
        viewModel.carregaListaObra()

        ll_layout_anterior.setOnClickListener { clicouAnterior() }
        ll_layout_proximo.setOnClickListener { clicouProximo() }
    }

    override fun clicouAnterior() {
        this.onBackPressed()
    }

    override fun clicouProximo(){

        try{
            if(obraSelecionada == null)
                throw Exception("Obra não selecionada.")

            val obra = this.listaObra[this.obraSelecionada!!]
            viewModel.confirmaPedido(Local(obra.id, obra.tipo, obra.codigo))

            val intent = Intent(this, SelecionaItemPedidoParaNucleoActivity::class.java)
            startActivity(intent)
        }
        catch(e: Exception){
            Snackbar.make(ll_all, e.message.toString(), Snackbar.LENGTH_LONG).show()
        }
    }

    fun processResponse(response: Response?) {
        when (response?.status) {
            Status.LOADING -> {}
            Status.SUCCESS -> {

                if(response.data is List<*>){

                    if(response.data[0] is Obra)
                        renderDataObra(response.data)
                }

            }
            Status.ERROR -> {}
        }
    }

    fun renderDataObra(result: Any?){

        val list = result as List<Obra>
        this.listaObra.addAll(list)

        val listaTextoOrigem = list.map { it.getTitulo() }
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,  listaTextoOrigem)
        spinner_obra.adapter = adapter

        spinner_obra.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View, position: Int, id: Long) {
                obraSelecionada = position
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {}
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
                    "Cancelar pedido ",
                    "Deseja sair e cancelar o pedido?",
                    {
                        finish()
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                        startActivity(intent)
                    },
                    {}).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}