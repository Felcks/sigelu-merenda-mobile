package com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento_novo.cadastra_recebimento_1_seleciona_envio

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
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ActivityDeFluxo
import com.lemobs_sigelu.gestao_estoques.common.domain.model.TwoIntParametersClickListener
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Status
import com.lemobs_sigelu.gestao_estoques.databinding.ActivityCrSelecionaEnvioBinding
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.FluxoInfo
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento_novo.cadastra_recebimento_2_cadastra_item.CRCadastraItemActivity
import com.lemobs_sigelu.gestao_estoques.ui.lista_pedidos.ListaPedidoActivity
import com.sigelu.core.lib.DialogUtil
import kotlinx.android.synthetic.main.activity_cr_seleciona_envio.*
import org.koin.android.ext.android.inject
import java.lang.Exception

class CRSelecionaEnvioActivity: AppCompatActivity(), ActivityDeFluxo, TwoIntParametersClickListener{

    val viewModel: CRSelecionaEnvioViewModel by inject()
    var tvErro: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cr_seleciona_envio)

        val binding: ActivityCrSelecionaEnvioBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_cr_seleciona_envio)
        binding.viewModel = viewModel
        binding.executePendingBindings()

        viewModel.listaEnvioResponse().observe(this, Observer<Response> { response -> processResponse(response) })

        this.tvErro = ll_erro.findViewById<TextView>(R.id.tv_erro)


        ll_erro.findViewById<AppCompatImageView>(R.id.iv_refresh).setOnClickListener {
            viewModel.carregaListaEnvio()
        }

        viewModel.getFluxo().setPassoAtual(1)
        viewModel.getFluxo().setMaximoPasso(FluxoInfo.RECEBIMENTO.maximoPassos)
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
            viewModel.iniciaRecebimento()

            viewModel.getFluxo().incrementaPassoAtual()
            val intent = Intent(this, CRCadastraItemActivity::class.java)
            startActivity(intent)
        }
        catch (e: Exception){
            viewModel.carregandoProximaTela.value = Response.empty()
            Snackbar.make(ll_all, e.message.toString(), Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun clicouAnterior() {
        this.onBackPressed()
    }

    fun processResponse(response: Response){
        when(response.status){
            Status.SUCCESS -> {
                if(response.data is List<*>){
                    if(response.data.isNotEmpty()){
                        if(response.data[0] is EnvioDTO){
                            iniciaListaEnvio(response.data as List<EnvioDTO>)
                        }
                    }
                }
            }
            Status.ERROR -> {
                tvErro?.text = resources.getString(R.string.erro_carrega_lista_envio)
            }
            else -> {
                tvErro?.text = resources.getString(R.string.erro_lista_envio_vazia)
            }
        }
    }

    private fun iniciaListaEnvio(listaEnvio: List<EnvioDTO>){


        val layoutManager = LinearLayoutManager(applicationContext)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_list.layoutManager = layoutManager

        val adapter = CRSelecionaEnvioAdapter(applicationContext,
            listaEnvio,
            this)
        rv_list.adapter = adapter
    }

    override fun onClick(id: Int, pos: Int) {
        viewModel.selecionaPedidoEstoqueEnvioID(id)
    }

    override fun onResume() {
        super.onResume()
        viewModel.carregaListaEnvio()
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