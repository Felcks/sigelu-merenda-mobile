package com.sigelu.merenda.ui.cadastra_pedido.cadastra_pedido_0_seleciona_obra

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
import com.sigelu.merenda.R
import com.sigelu.merenda.common.domain.model.ActivityDeFluxo
import com.sigelu.merenda.common.viewmodel.Response
import com.sigelu.merenda.common.viewmodel.Status
import com.sigelu.merenda.databinding.ActivityCpSelecionaObraBinding
import com.sigelu.merenda.ui.cadastra_pedido.cadastra_pedido_1_seleciona_item.SelecionaItemPedidoParaNucleoActivity
import com.sigelu.merenda.ui.lista_pedidos.ListaPedidoActivity
import com.sigelu.core.lib.DialogUtil
import kotlinx.android.synthetic.main.activity_cp_seleciona_obra.*
import org.koin.android.ext.android.inject
import java.lang.Exception

class SelecionaObraActivity: AppCompatActivity(), ActivityDeFluxo {

    val viewModel: SelecionaObraViewModel by inject()
    private var adapter: SelecionaObraAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cp_seleciona_obra)

        val binding: ActivityCpSelecionaObraBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_cp_seleciona_obra)
        binding.viewModel = viewModel
        binding.executePendingBindings()

        val tvErro = ll_erro.findViewById<TextView>(R.id.tv_erro)
        tvErro?.text = resources.getString(R.string.erro_carrega_lista_obra)
        ll_erro.findViewById<AppCompatImageView>(R.id.iv_refresh).setOnClickListener {
            viewModel.carregaListaObra()
        }

        viewModel.listaObra.observe(this, Observer<Response> { response -> processResponse(response) })
        viewModel.carregaListaObra()

        this.iniciaStepper()
    }

    fun iniciaStepper(){
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
            viewModel.setPosObraSelecionada(adapter?.getPosicaoSelecionada())
            viewModel.confirmaPedido()

            viewModel.getFluxo().incrementaPassoAtual()
            val intent = Intent(this, SelecionaItemPedidoParaNucleoActivity::class.java)
            startActivity(intent)
        }
        catch(e: Exception){
            viewModel.carregandoProximaTela.value = Response.empty()
            Snackbar.make(ll_all, e.message.toString(), Snackbar.LENGTH_SHORT).show()
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

        val layoutManager = LinearLayoutManager(applicationContext)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_lista.layoutManager = layoutManager

        this.adapter = SelecionaObraAdapter(
            list
        )
        rv_lista.adapter = adapter

//        val listaTextoOrigem = list.map { it.nome }
//        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,  listaTextoOrigem)
//        spinner_obra.adapter = adapter
//
//        spinner_obra.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//
//            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View, position: Int, id: Long) {
//                viewModel.setPosObraSelecionada(position)
//            }
//            override fun onNothingSelected(parentView: AdapterView<*>) {}
//        }
    }

    override fun onResume() {
        super.onResume()
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
                    this@SelecionaObraActivity.getString(R.string.dialogo_titulo_cancelar_requisicao),
                    this@SelecionaObraActivity.getString(R.string.dialogo_mensagem_cancelar_requisicao),
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