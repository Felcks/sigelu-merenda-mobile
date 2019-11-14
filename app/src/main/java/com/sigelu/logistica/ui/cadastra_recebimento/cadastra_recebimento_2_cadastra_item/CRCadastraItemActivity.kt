package com.sigelu.logistica.ui.cadastra_recebimento.cadastra_recebimento_2_cadastra_item

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.sigelu.logistica.R
import com.sigelu.logistica.common.domain.model.ActivityDeFluxo
import com.sigelu.logistica.common.viewmodel.Response
import com.sigelu.logistica.common.viewmodel.Status
import com.sigelu.logistica.databinding.ActivityCrCadastraQuantidadeBinding
import com.sigelu.logistica.ui.cadastra_recebimento.cadastra_recebimento_3_confirma.CRConfirmaActivity
import com.sigelu.logistica.ui.lista_pedidos.ListaPedidoActivity
import com.sigelu.core.lib.DialogUtil
import kotlinx.android.synthetic.main.activity_cr_cadastra_quantidade.*
import org.koin.android.ext.android.inject
import java.lang.Exception

class CRCadastraItemActivity: AppCompatActivity(), ActivityDeFluxo {

    private val viewModel: CRCadastraItemViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cr_cadastra_quantidade)

        val binding: ActivityCrCadastraQuantidadeBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_cr_cadastra_quantidade)
        binding.viewModel = viewModel
        binding.executePendingBindings()

        viewModel.listaItemResponse.observe(this, Observer<Response> { response -> processResponse(response)})

        val tvErro = ll_erro.findViewById<TextView>(R.id.tv_erro)
        tvErro?.text = resources.getString(R.string.erro_carrega_lista_envio)

        ll_erro.findViewById<AppCompatImageView>(R.id.iv_refresh).setOnClickListener {
            viewModel.carregaListaItemEnvio()
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
            val lista = adapter?.getListaMateriaisPreenchidos()
            viewModel.confirmaCadastroItem(lista ?: listOf())
            viewModel.getFluxo().incrementaPassoAtual()

            val intent = Intent(this, CRConfirmaActivity::class.java)
            startActivity(intent)
        }
        catch(e: Exception){
            viewModel.carregandoProximaTela.value = Response.empty()
            Snackbar.make(ll_all, e.message.toString(), Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun clicouAnterior() {
        onBackPressed()
    }

    private fun processResponse(response: Response){

        when(response.status){
            Status.SUCCESS -> {
                if(response.data is List<*>){
                    if(response.data.isNotEmpty()){
                        if(response.data[0] is ItemRecebimentoDTO){
                            this.iniciaLista(response.data as List<ItemRecebimentoDTO>)
                        }
                    }
                }
            }
            else -> {}
        }
    }

    private var adapter: CRCadastraItemAdapter? = null
    private fun iniciaLista(listaItem: List<ItemRecebimentoDTO>){

        val layoutManager = LinearLayoutManager(applicationContext)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_list.layoutManager = layoutManager

        this.adapter = CRCadastraItemAdapter(applicationContext,
            listaItem)
        rv_list.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        viewModel.carregaListaItemEnvio()
        viewModel.carregandoProximaTela.value = Response.empty()
    }

    override fun onBackPressed() {
        viewModel.getFluxo().decrementaPassoAtual()
        super.onBackPressed()
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