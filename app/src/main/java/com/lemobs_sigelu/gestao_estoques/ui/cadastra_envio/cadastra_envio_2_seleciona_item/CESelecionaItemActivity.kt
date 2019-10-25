package com.lemobs_sigelu.gestao_estoques.ui.cadastra_envio.cadastra_envio_2_seleciona_item

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ActivityDeFluxo
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEstoque
import com.lemobs_sigelu.gestao_estoques.common.domain.model.TwoIntParametersClickListener
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Status
import com.lemobs_sigelu.gestao_estoques.databinding.ActivityCeSelecionaItemBinding
import com.lemobs_sigelu.gestao_estoques.exceptions.NenhumItemSelecionadoException
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_envio.cadastra_envio_3_cadastra_item.CECadastraItemActivity
import com.lemobs_sigelu.gestao_estoques.ui.lista_pedidos.ListaPedidoActivity
import com.sigelu.core.lib.DialogUtil
import kotlinx.android.synthetic.main.activity_ce_seleciona_item.*
import org.koin.android.ext.android.inject

class CESelecionaItemActivity: AppCompatActivity(), ActivityDeFluxo, TwoIntParametersClickListener {

    val viewModel: CESelecionaItemViewModel by inject()
    var adapter: ListaItemEstoqueAdapterSimples? = null

    var tvErro: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ce_seleciona_item)

        val binding: ActivityCeSelecionaItemBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_ce_seleciona_item
        )
        binding.viewModel = viewModel
        binding.executePendingBindings()

        tvErro = ll_erro.findViewById(R.id.tv_erro)
        ll_erro.findViewById<AppCompatImageView>(R.id.iv_refresh).setOnClickListener {
            viewModel.carregaListagemItem()
        }

        viewModel.listaItemEstoque().observe(this, Observer<Response> { response -> processResponse(response) })
        this.iniciaStepper()
    }

    private fun iniciaStepper(){
        top_stepper.setFluxo(viewModel.getFluxo())
        bottom_stepper.setFluxo(viewModel.getFluxo())

        top_stepper.atualiza()
        bottom_stepper.atualiza()

        bottom_stepper.setAnteriorOnClickListener { clicouAnterior() }
        bottom_stepper.setProximoOnClickListener { clicouProximo() }
    }

    override fun clicouProximo() {

        if(viewModel.carregandoProximaTela.value?.status != Status.EMPTY_RESPONSE)
            return

        try{
            viewModel.carregandoProximaTela.value = Response.loading()
            if(this.adapter == null)
                throw NenhumItemSelecionadoException()

            viewModel.confirmaSelecaoItens(
                this.adapter?.itemsParaAdicao as List<ItemEstoque>,
                this.adapter?.itemsParaRemocao as List<ItemEstoque>)


            viewModel.getFluxo().incrementaPassoAtual()
            val intent = Intent(this, CECadastraItemActivity::class.java)
            startActivity(intent)
        }
        catch(e: Exception){
            viewModel.carregandoProximaTela.value = Response.empty()
            Snackbar.make(ll_all, e.message.toString(), Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun clicouAnterior() {
        this.onBackPressed()
    }

    fun processResponse(response: Response){

        when(response.status){
            Status.ERROR -> {
                tvErro?.text = resources.getString(R.string.erro_carrega_lista_item_estoque)
            }
            Status.EMPTY_RESPONSE -> {
                tvErro?.text = resources.getString(R.string.erro_nenhum_item_cadastrado)
            }
            Status.SUCCESS -> {
                if(response.data is List<*>) {
                    if (response.data.isNotEmpty()) {
                        if (response.data[0] is ItemEstoque) {
                            this.iniciaLista(response.data as List<ItemEstoque>)
                        }
                    }
                }
            }
            else -> {}
        }
    }

    private fun iniciaLista(lista: List<ItemEstoque>){

        val layoutManager = LinearLayoutManager(applicationContext)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_lista.layoutManager = layoutManager

        this.adapter = ListaItemEstoqueAdapterSimples(applicationContext,
            lista,
            this,
            viewModel.getIDsDeItemAdicionados())
        rv_lista.adapter = adapter
    }

    override fun onClick(id: Int, pos: Int) {
        try{
            val adicionou = viewModel.veriricaSeItemJaEstaAdicionado(id)

            if(adicionou){ adapter?.adicionaItem(pos) }
            else{ adapter?.removeItem(pos) }
        }
        catch (e: Exception){
            Toast.makeText(applicationContext, "Ocorreu algum erro", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onBackPressed() {
        viewModel.getFluxo().decrementaPassoAtual()
        super.onBackPressed()
    }

    override fun onResume() {
        viewModel.carregandoProximaTela.value = Response.empty()
        viewModel.carregaListagemItem()
        super.onResume()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == android.R.id.home){
            val intent = Intent(applicationContext, ListaPedidoActivity::class.java)
            DialogUtil.buildAlertDialogSimNao(
                this,
                "Cancelar envio ",
                "Deseja sair e cancelar o envio?",
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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}