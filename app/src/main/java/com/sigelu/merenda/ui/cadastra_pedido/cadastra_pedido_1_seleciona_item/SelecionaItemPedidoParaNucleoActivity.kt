package com.sigelu.merenda.ui.cadastra_pedido.cadastra_pedido_1_seleciona_item

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.sigelu.merenda.R
import com.sigelu.merenda.common.domain.model.ActivityDeFluxo
import com.sigelu.merenda.common.domain.model.TwoIntParametersClickListener
import com.sigelu.merenda.common.viewmodel.Response
import com.sigelu.merenda.common.viewmodel.Status
import com.sigelu.merenda.databinding.ActivityCpSelecionaMaterialBinding
import com.sigelu.merenda.extensions_constants.SITUACAO_RASCUNHO
import com.sigelu.merenda.ui.cadastra_pedido.FluxoInfo
import com.sigelu.merenda.ui.cadastra_pedido.cadastra_pedido_2_cadastra_item.CadastraItemPedidoActivity
import com.sigelu.merenda.ui.lista_pedidos.ListaPedidoActivity
import com.sigelu.core.lib.DialogUtil
import kotlinx.android.synthetic.main.activity_cp_seleciona_material.*
import kotlinx.android.synthetic.main.activity_cp_seleciona_material.rv_lista
import org.koin.android.ext.android.inject


class SelecionaItemPedidoParaNucleoActivity: AppCompatActivity(), TwoIntParametersClickListener, ActivityDeFluxo {

    private val viewModel: SelecionaItemPedidoParaNucleoViewModel by inject()
    private var adapter : ListaItemEstoqueAdapterSimples? = null
    private var tvErro: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cp_seleciona_material)

        val binding: ActivityCpSelecionaMaterialBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_cp_seleciona_material)
        binding.viewModel = viewModel
        binding.executePendingBindings()

        viewModel.listaItemEstoque.observe(this, Observer<Response> {
                response -> processResponse(response)
        })

        tvErro = ll_erro.findViewById<TextView>(R.id.tv_erro)
        tvErro?.text = resources.getString(R.string.erro_carrega_lista_item_estoque)

        ll_erro.findViewById<AppCompatImageView>(R.id.iv_refresh).setOnClickListener {
            viewModel.refreshListaItemEstoque()
        }

        if(viewModel.getPedido().getSituacao()?.situacao_id == SITUACAO_RASCUNHO) {
            viewModel.getFluxo().setPassoAtual(1)
            viewModel.getFluxo().setMaximoPasso(FluxoInfo.NUCLEO.maximoPassos - 1)
        }

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
                this.adapter?.itemsParaAdicao?.map { it.id } ?: listOf(),
                this.adapter?.itemsParaRemocao?.map { it.id } ?: listOf())

            viewModel.getFluxo().incrementaPassoAtual()
            val intent = Intent(this, CadastraItemPedidoActivity::class.java)
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

    override fun onResume() {
        viewModel.refreshListaItemEstoque()
        viewModel.carregandoProximaTela.value = Response.empty()
        super.onResume()
    }

    override fun onBackPressed() {
        viewModel.getFluxo().decrementaPassoAtual()
        super.onBackPressed()
    }

    fun processResponse(response: Response){

        when(response.status){
            Status.LOADING -> { }
            Status.ERROR -> {
                tvErro?.text = resources.getString(R.string.erro_carrega_lista_item_estoque)
            }
            Status.SUCCESS -> {

                val layoutManager = LinearLayoutManager(applicationContext)
                layoutManager.orientation = LinearLayoutManager.VERTICAL
                rv_lista.layoutManager = layoutManager

                this.adapter = ListaItemEstoqueAdapterSimples(applicationContext,
                    response.data as? List<ItemEstoqueDTO> ?: listOf(),
                    this,
                    viewModel.getIDsDeItemAdicionados())
                rv_lista.adapter = adapter
            }
            Status.EMPTY_RESPONSE -> {
                tvErro?.text = resources.getString(R.string.erro_nenhum_item_cadastrado)
            }
        }
    }

    override fun onClick(id: Int, pos: Int) {
        try{
            val isItemAdicionado = viewModel.veriricaSeItemJaEstaAdicionado(id)

            if(!isItemAdicionado){ adapter?.adicionaItem(pos) }
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
                this@SelecionaItemPedidoParaNucleoActivity.getString(R.string.dialogo_titulo_cancelar_requisicao),
                this@SelecionaItemPedidoParaNucleoActivity.getString(R.string.dialogo_mensagem_cancelar_requisicao),
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