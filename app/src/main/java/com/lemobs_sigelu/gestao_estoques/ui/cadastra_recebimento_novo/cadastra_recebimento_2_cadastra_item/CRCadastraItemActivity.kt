package com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento_novo.cadastra_recebimento_2_cadastra_item

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.DataBindingUtil
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ActivityDeFluxo
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.databinding.ActivityCrCadastraQuantidadeBinding
import com.lemobs_sigelu.gestao_estoques.ui.lista_pedidos.ListaPedidoActivity
import com.sigelu.core.lib.DialogUtil
import kotlinx.android.synthetic.main.activity_cr_cadastra_quantidade.*
import org.koin.android.ext.android.inject

class CRCadastraItemActivity: AppCompatActivity(), ActivityDeFluxo {

    private val viewModel: CRCadastraItemViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cr_cadastra_quantidade)

        val binding: ActivityCrCadastraQuantidadeBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_cr_cadastra_quantidade)
        binding.viewModel = viewModel
        binding.executePendingBindings()

        val tvErro = ll_erro.findViewById<TextView>(R.id.tv_erro)
        tvErro?.text = resources.getString(R.string.erro_carrega_lista_envio)

        ll_erro.findViewById<AppCompatImageView>(R.id.iv_refresh).setOnClickListener {
            //viewModel.carregaListaEnvio()
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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clicouAnterior() {
        onBackPressed()
    }

    override fun onResume() {
        super.onResume()
        //viewModel.carregaListaEnvio()
        viewModel.carregandoProximaTela.value = Response.empty()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        viewModel.getFluxo().decrementaPassoAtual()
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
                    "Cancelar recebimento",
                    "Deseja sair e cancelar o recebimento?",
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