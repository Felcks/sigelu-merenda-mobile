package com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_1_seleciona_item

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
import com.lemobs_sigelu.gestao_estoques.common.domain.model.TwoIntParametersClickListener
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Status
import com.lemobs_sigelu.gestao_estoques.databinding.ActivitySelecionaMaterialPedidoOriginalBinding
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_2_cadastra_item.CadastraItemPedidoActivity
import com.lemobs_sigelu.gestao_estoques.ui.lista_pedidos.ListaPedidoActivity
import com.sigelu.core.lib.DialogUtil
import kotlinx.android.synthetic.main.activity_seleciona_material_pedido_original.*
import kotlinx.android.synthetic.main.activity_seleciona_material_pedido_original.ll_loading
import kotlinx.android.synthetic.main.activity_seleciona_material_pedido_original.rv_lista
import org.koin.android.ext.android.inject


class SelecionaItemPedidoParaNucleoActivity: AppCompatActivity(), TwoIntParametersClickListener, ActivityDeFluxo {

    private val viewModel: SelecionaItemPedidoParaNucleoViewModel by inject()
    private var adapter : ListaItemEstoqueAdapterSimples? = null
    private var tvErro: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seleciona_material_pedido_original)

        val binding: ActivitySelecionaMaterialPedidoOriginalBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_seleciona_material_pedido_original)
        binding.viewModel = viewModel
        binding.executePendingBindings()

        carregaListaItemEstoque()

        ll_layout_anterior.setOnClickListener { clicouAnterior() }
        ll_layout_proximo.setOnClickListener { clicouProximo() }

        tvErro = ll_erro.findViewById<TextView>(R.id.tv_erro)
        tvErro?.text = resources.getString(R.string.erro_carrega_lista_item_estoque)

        ll_erro.findViewById<AppCompatImageView>(R.id.iv_refresh).setOnClickListener {
            viewModel.refreshListaItemEstoque()
        }
    }

    private fun carregaListaItemEstoque(){
        viewModel.listaItemEstoque.observe(this, Observer<Response> {
                response -> processResponse(response)
        })
    }

    override fun clicouProximo() {
        try{
            viewModel.confirmaSelecaoItens(
                this.adapter?.itemsParaAdicao?.map { it.id } ?: listOf(),
                this.adapter?.itemsParaRemocao?.map { it.id } ?: listOf())

            val intent = Intent(this, CadastraItemPedidoActivity::class.java)
            startActivity(intent)
        }
        catch(e: Exception){
            Snackbar.make(ll_all, e.message.toString(), Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun clicouAnterior() {
        this.onBackPressed()
    }

    override fun onResume() {
        viewModel.refreshListaItemEstoque()
        super.onResume()
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
                "Cancelar pedido ",
                "Deseja sair e cancelar o pedido?",
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