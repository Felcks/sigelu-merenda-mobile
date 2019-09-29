package com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento_novo.cadastra_recebimento_3_confirma

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ActivityDeFluxo
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Status
import com.lemobs_sigelu.gestao_estoques.databinding.ActivityCrConfirmaBinding
import com.lemobs_sigelu.gestao_estoques.ui.lista_pedidos.ListaPedidoActivity
import com.lemobs_sigelu.gestao_estoques.utils.AlertDialogView
import com.sigelu.core.lib.DialogUtil
import kotlinx.android.synthetic.main.activity_cr_confirma.*
import org.koin.android.ext.android.inject
class CRConfirmaActivity: AppCompatActivity(), ActivityDeFluxo {

    val viewModel: CRConfirmaViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cr_confirma)

        val binding: ActivityCrConfirmaBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_cr_confirma)
        binding.viewModel = viewModel
        binding.executePendingBindings()

        viewModel.listaItemRecebimentoResponse.observe(this, Observer<Response>{ response -> processResponse(response) })
        viewModel.cadastroRecebimentoResponse.observe(this, Observer<Response> { response -> processResponseCadastroRecebimento(response)})

        val tvErro = ll_erro.findViewById<TextView>(R.id.tv_erro)
        tvErro?.text = resources.getString(R.string.erro_carrega_lista_item_envio)

        ll_erro.findViewById<AppCompatImageView>(R.id.iv_refresh).setOnClickListener {
            viewModel.carregaListaItemRecebimento()
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
        viewModel.cadastraRecebimento()
    }

    override fun clicouAnterior() {
        onBackPressed()
    }

    fun processResponse(response: Response){

        when(response.status){
            Status.SUCCESS -> {
                if(response.data is List<*>){
                    if(response.data.isNotEmpty()){
                        if(response.data[0] is ItemRecebimentoDTO){
                            iniciaListaItemRecebimento(response.data as List<ItemRecebimentoDTO>)
                        }
                    }
                }
            }
            else -> {}
        }
    }

    fun processResponseCadastroRecebimento(response: Response){

        when(response.status){
            Status.LOADING -> renderLoadingCadastroRecebimento()
            Status.SUCCESS -> renderSucessoRecebimento(response.data)
            Status.ERROR -> renderErroCadastroRecebimento(response.error)
            else -> {}
        }
    }

    private fun iniciaListaItemRecebimento(lista: List<ItemRecebimentoDTO>){

        val layoutManager = LinearLayoutManager(applicationContext)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_list.layoutManager = layoutManager

        val adapter = CRConfirmaAdapter(applicationContext, lista)
        rv_list.adapter = adapter
    }


    var progressDialog: ProgressDialog? = null
    private fun renderLoadingCadastroRecebimento() {

        progressDialog = DialogUtil.buildDialogCarregamento(this,
            "Cadastrando recebimento",
            "Por favor, espere...")
    }

    var sucessDialog: AlertDialogView? = null
    private fun renderSucessoRecebimento(result: Any?){

        progressDialog?.dismiss()

        val activity = this
        this.sucessDialog = DialogUtil.buildAlertDialogOk(this,
            "Sucesso",
            "Recebimento cadastrado com sucesso!",
            {
                val intent = Intent(activity, ListaPedidoActivity::class.java)
                startActivity(intent)
                this.finishAffinity()
            },
            false)

        this.sucessDialog?.show()
    }

    var errorDialog: AlertDialogView? = null
    private fun renderErroCadastroRecebimento(error: Throwable?){

        progressDialog?.dismiss()

        this.errorDialog = DialogUtil.buildAlertDialogOk(this,
            "Erro",
            "Ocorreu um erro ao cadastrar recebimento. Contate o administrador do sistema.",
            {},
            true)

        this.errorDialog?.show()
    }

    override fun onBackPressed() {
        viewModel.getFluxo().decrementaPassoAtual()
        super.onBackPressed()
    }

    override fun onResume() {
        super.onResume()
        viewModel.carregaListaItemRecebimento()
    }
}