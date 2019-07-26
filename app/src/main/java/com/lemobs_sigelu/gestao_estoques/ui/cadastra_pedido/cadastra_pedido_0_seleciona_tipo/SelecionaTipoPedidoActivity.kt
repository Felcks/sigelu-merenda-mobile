package com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_0_seleciona_tipo

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.domain.model.TipoPedido
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.CadastraPedidoViewModelFactory
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_1_1_fornecedor.CadastraFornecedorActivity
import com.lemobs_sigelu.gestao_estoques.ui.lista_pedidos.ListaPedidoActivity
import com.lemobs_sigelu.gestao_estoques.ui.pedido.activity.VisualizarPedidoActivity
import com.sigelu.core.lib.DialogUtil
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_1_2_nucleo.CadastraNucleoActivity
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_seleciona_tipo_pedido.*
import javax.inject.Inject

class SelecionaTipoPedidoActivity: AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: CadastraPedidoViewModelFactory
    var viewModel: SelecionaTipoPedidoViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seleciona_tipo_pedido)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SelecionaTipoPedidoViewModel::class.java)
        viewModel!!.selecionaTipoPedido(TipoPedido.FORNECEDOR_PARA_MEU_NUCLEO)

        ll_layout_anterior.setOnClickListener {
            onBackPressed()
        }

        ll_layout_proximo.setOnClickListener {
            clicouNoProximo()
        }
    }

    fun onFornecedorClicked(v: View){
        rb_obra.isChecked = false
        rb_nucleo.isChecked = false
        tv_proximo.text = "Próximo: Fornecedor"
        viewModel!!.selecionaTipoPedido(TipoPedido.FORNECEDOR_PARA_MEU_NUCLEO)
    }

    fun onNucleoClicked(v: View){
        rb_obra.isChecked = false
        rb_fornecedor.isChecked = false
        tv_proximo.text = "Próximo: Núcleo"
        viewModel!!.selecionaTipoPedido(TipoPedido.OUTRO_NUCLEO_PARA_MEU_NUCLEO)
    }

    fun onObraClicked(v: View){
        rb_nucleo.isChecked = false
        rb_fornecedor.isChecked = false
        tv_proximo.text = "Próximo: Obra"
        viewModel!!.selecionaTipoPedido(TipoPedido.MEU_NUCLEO_PARA_OBRA)
    }

    fun clicouNoProximo(){

        val tipoPedido = viewModel!!.getInicialTipoPedido()

        if(tipoPedido == TipoPedido.FORNECEDOR_PARA_MEU_NUCLEO){
            val intent = Intent(this, CadastraFornecedorActivity::class.java)
            startActivity(intent)
        }
        else if(tipoPedido == TipoPedido.OUTRO_NUCLEO_PARA_MEU_NUCLEO){
            val intent = Intent(this, CadastraNucleoActivity::class.java)
            startActivity(intent)
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
