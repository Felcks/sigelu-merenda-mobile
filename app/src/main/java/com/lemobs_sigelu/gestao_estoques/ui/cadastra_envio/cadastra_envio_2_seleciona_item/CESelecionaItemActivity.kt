package com.lemobs_sigelu.gestao_estoques.ui.cadastra_envio.cadastra_envio_2_seleciona_item

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ActivityDeFluxo
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEstoque
import com.lemobs_sigelu.gestao_estoques.common.domain.model.TwoIntParametersClickListener
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Status
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_envio.cadastra_envio_3_cadastra_item.CECadastraItemActivity
import com.lemobs_sigelu.gestao_estoques.ui.lista_pedidos.ListaPedidoActivity
import com.sigelu.core.lib.DialogUtil
import kotlinx.android.synthetic.main.activity_cadastra_envio_seleciona_item.*
import org.koin.android.ext.android.inject

class CESelecionaItemActivity: AppCompatActivity(), ActivityDeFluxo, TwoIntParametersClickListener {

    val viewModel: CESelecionaItemViewModel by inject()
    var adapter: ListaItemEstoqueAdapterSimples? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastra_envio_seleciona_item)

        viewModel.listaItemEstoque().observe(this, Observer<Response> { response -> processResponse(response) })
        viewModel.carregaListagemItem()

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
        viewModel.getFluxo().decrementaPassoAtual()
    }

    override fun onResume() {
        viewModel.carregaListagemItem()
        viewModel.carregandoProximaTela.value = Response.empty()
        super.onResume()
    }

    fun processResponse(response: Response){

        when(response.status){
            Status.LOADING -> {
                ll_loading.visibility = View.VISIBLE
                rv_lista.visibility = View.GONE
            }
            Status.ERROR -> {
                ll_loading.visibility = View.GONE
                rv_lista.visibility = View.GONE
                tv_error.visibility = View.VISIBLE
            }
            Status.SUCCESS -> {
                ll_loading.visibility = View.GONE
                rv_lista.visibility = View.VISIBLE

                val layoutManager = LinearLayoutManager(applicationContext)
                layoutManager.orientation = LinearLayoutManager.VERTICAL
                rv_lista.layoutManager = layoutManager

                this.adapter = ListaItemEstoqueAdapterSimples(applicationContext,
                    response.data as? List<ItemEstoque> ?: listOf(),
                    this,
                    viewModel.getIDsDeItemAdicionados())
                rv_lista.adapter = adapter
            }
        }
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