package com.sigelu.logistica.ui.cadastra_pedido.cadastra_pedido_0_seleciona_tipo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.sigelu.logistica.R
import com.sigelu.logistica.common.domain.model.ActivityDeFluxo
import com.sigelu.logistica.common.viewmodel.Response
import com.sigelu.logistica.common.viewmodel.Status
import com.sigelu.logistica.ui.cadastra_pedido.FluxoInfo
import com.sigelu.logistica.ui.lista_pedidos.ListaPedidoActivity
import com.sigelu.core.lib.DialogUtil
import kotlinx.android.synthetic.main.activity_cp_seleciona_tipo_pedido.*
import kotlinx.android.synthetic.main.activity_cp_seleciona_tipo_pedido.bottom_stepper
import kotlinx.android.synthetic.main.activity_cp_seleciona_tipo_pedido.ll_all
import org.koin.android.ext.android.inject
import java.lang.Exception

class SelecionaTipoPedidoActivity: AppCompatActivity(), ActivityDeFluxo {

    private val viewModel: SelecionaTipoPedidoViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cp_seleciona_tipo_pedido)

        viewModel.proximaTela().observe(this, Observer<Response> { response -> observarMudancaDeTela(response) })

        viewModel.getFluxo().setPassoAtual(1)
        viewModel.getFluxo().setMaximoPasso(FluxoInfo.NUCLEO.maximoPassos)

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

        if(viewModel.proximaTela().value?.status != Status.EMPTY_RESPONSE)
            return

        try {
            viewModel.confirmaDestinoPedido()
            viewModel.getFluxo().incrementaPassoAtual()
        }
        catch (e: Exception){
            viewModel.proximaTela().value = Response.empty()
            Snackbar.make(ll_all, e.message ?: "Ocorreu um erro inesperado.", Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun clicouAnterior() {
        this.onBackPressed()
    }

    private fun observarMudancaDeTela(response: Response?){

        if(response != null && response.status == Status.SUCCESS){
            if(response.data is Intent){

                viewModel.setProximaTelaUndefined()
                startActivity(response.data)
            }
        }
    }

    fun clickPrimeiroRadioButton(v: View){
        rb_opcao_2.isChecked = false
        rb_opcao_3.isChecked = false
        viewModel.getFluxo().setMaximoPasso(FluxoInfo.NUCLEO.maximoPassos)
        top_stepper.atualiza()
        bottom_stepper.atualiza()
        viewModel.selecionaTipoPedido(0)
    }

    fun clickSegundoRadioButton(v: View){
        rb_opcao_1.isChecked = false
        rb_opcao_3.isChecked = false
        viewModel.getFluxo().setMaximoPasso(FluxoInfo.OBRA.maximoPassos)
        top_stepper.atualiza()
        bottom_stepper.atualiza()
        viewModel.selecionaTipoPedido(1)
    }

    override fun onResume() {
        super.onResume()
        viewModel.proximaTela().value = Response.empty()
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
                    "Cancelar RM",
                    "Deseja sair e cancelar a RM?",
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
