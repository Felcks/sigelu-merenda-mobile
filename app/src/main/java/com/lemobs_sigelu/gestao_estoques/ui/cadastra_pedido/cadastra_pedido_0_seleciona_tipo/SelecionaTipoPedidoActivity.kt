package com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_0_seleciona_tipo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ActivityDeFluxo
import com.lemobs_sigelu.gestao_estoques.common.domain.model.TipoPedido
import com.lemobs_sigelu.gestao_estoques.ui.lista_pedidos.ListaPedidoActivity
import com.sigelu.core.lib.DialogUtil
import kotlinx.android.synthetic.main.activity_seleciona_tipo_pedido.*
import org.koin.android.ext.android.inject
import java.lang.Exception

class SelecionaTipoPedidoActivity: AppCompatActivity(), ActivityDeFluxo {

    private val viewModel: SelecionaTipoPedidoViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seleciona_tipo_pedido)

        ll_layout_anterior.setOnClickListener { clicouAnterior() }
        ll_layout_proximo.setOnClickListener { clicouProximo() }
    }

    override fun clicouProximo() {

        try {
            val proximaTelaIntent = viewModel.confirmaDestinoDePedido()
            startActivity(proximaTelaIntent)
            return
        }
        catch (e: Exception){
            Snackbar.make(ll_all, e.message ?: "Ocorreu um erro inesperado.", Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun clicouAnterior() {
        this.onBackPressed()
    }

    fun clickPrimeiroRadioButton(v: View){
        rb_nucleo.isChecked = false
        tv_proximo.text = "Próximo: Materiais"
        viewModel.selecionaTipoPedido(TipoPedido.FORNECEDOR_PARA_MEU_NUCLEO)
    }

    fun clickSegundoRadioButton(v: View){
        rb_fornecedor.isChecked = false
        tv_proximo.text = "Próximo: Materiais"
        viewModel.selecionaTipoPedido(TipoPedido.FORNECEDOR_PARA_OBRA)
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
                    "Cancelar pedido",
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
