package com.lemobs_sigelu.gestao_estoques.ui.cadastra_envio.cadastra_envio_0_seleciona_obra

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
import com.google.android.material.snackbar.Snackbar
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ActivityDeFluxo
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Status
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_envio.cadastra_envio_2_seleciona_item.CESelecionaItemActivity
import com.lemobs_sigelu.gestao_estoques.ui.lista_pedidos.ListaPedidoActivity
import com.sigelu.core.lib.DialogUtil
import kotlinx.android.synthetic.main.activity_cadastra_envio_seleciona_obra.*
import org.koin.android.ext.android.inject
import java.lang.Exception

class CESelecionaObraActivity: AppCompatActivity(), ActivityDeFluxo  {

    val viewModel: CESelecionaObraViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastra_envio_seleciona_obra)

        viewModel.listaObra.observe(this, Observer<Response> { response -> processResponse(response) })
        viewModel.carregaListaObra()

        viewModel.getFluxo().setPassoAtual(1)
        viewModel.getFluxo().setMaximoPasso(4)

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

    override fun clicouAnterior() {
        this.onBackPressed()
    }

    override fun clicouProximo(){

        if(viewModel.carregandoProximaTela.value?.status != Status.EMPTY_RESPONSE)
            return

        try{
            viewModel.carregandoProximaTela.value = Response.loading()
            viewModel.confirmaPedido()

            viewModel.getFluxo().incrementaPassoAtual()
            val intent = Intent(this, CESelecionaItemActivity::class.java)
            startActivity(intent)
        }
        catch(e: Exception){
            Snackbar.make(ll_all, e.message.toString(), Snackbar.LENGTH_LONG).show()
        }
        finally {
            viewModel.carregandoProximaTela.value = Response.empty()
        }
    }

    fun processResponse(response: Response?) {
        when (response?.status) {
            Status.LOADING -> {}
            Status.SUCCESS -> {

                if(response.data is List<*>){

                    if(response.data.isNotEmpty()) {
                        if (response.data[0] is ObraDTO)
                            renderDataObra(response.data as List<ObraDTO>)
                    }
                }
            }
            Status.ERROR -> {}
        }
    }

    private fun renderDataObra(list: List<ObraDTO>){

        val listaTextoOrigem = list.map { it.titulo }
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,  listaTextoOrigem)
        spinner_obra.adapter = adapter

        spinner_obra.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View, position: Int, id: Long) {
                viewModel.setPosObraSelecionada(position)
            }
            override fun onNothingSelected(parentView: AdapterView<*>) {}
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.carregandoProximaTela.value = Response.empty()
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
                    "Cancelar envio ",
                    "Deseja sair e cancelar o envio?",
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